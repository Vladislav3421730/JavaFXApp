package org.example.TCP;

import com.google.gson.Gson;
import org.example.TCP.enums.ResponseType;
import org.example.models.Equipment;
import org.example.models.Patent;
import org.example.models.Project;
import org.example.models.User;
import org.example.models.enums.Role;
import org.example.services.EquipmentService;
import org.example.services.PatentService;
import org.example.services.ProjectService;
import org.example.services.UserService;
import org.example.utils.PasswordEncoder;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket clientSocket;
    private Request request;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket clientSocket) throws IOException {
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    private final UserService userService = UserService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();
    private final PatentService patentService = PatentService.getInstance();
    private final EquipmentService equipmentService = EquipmentService.getInstance();

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                request = gson.fromJson(in.readLine(), Request.class);
                System.out.println(gson.toJson(request));
                switch (request.getRequestType()) {
                    case REGISTER: {
                        User user = gson.fromJson(request.getMessage(), User.class);
                        if (userService.FindByLogin(user.getLogin()) != null) {
                            out.println(gson.toJson(
                                    new Response("Такой пользователь уже есть зарегистрирован", ResponseType.BAD_REQUEST)));
                        } else {
                            userService.Save(user);
                            out.println(gson.toJson(new Response("Вы успешно зарегистрировались", ResponseType.OK)));
                        }
                        out.flush();
                        break;
                    }
                    case LOGIN: {
                        User user = gson.fromJson(request.getMessage(), User.class);
                        User userDB = userService.FindByLogin(user.getLogin());
                        if (userDB == null || !userDB.getPassword().equals(PasswordEncoder.hashPassword(user.getPassword()))) {
                            out.println(gson.toJson(new Response("Неверный логин или пароль", ResponseType.NOT_FOUND)));
                        } else if (userDB.isBun()) {
                            out.println(gson.toJson(new Response("Вы были забанены", ResponseType.FORBIDDEN)));
                        } else {
                            System.out.println("Логин пользователя: " + userDB.getLogin());
                            out.println(gson.toJson(new Response(gson.toJson(userDB), ResponseType.OK)));
                        }
                        out.flush();
                        break;
                    }
                    case GET_ALL_USERS: {
                        out.println(gson.toJson(new Response(gson.toJson(userService.FindAll()), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case BUN_USER: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        User user = userService.FindById(id);
                        if (user == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Пользователь не найден"), ResponseType.NOT_FOUND)));
                            break;
                        }
                        String message = user.isBun() ? "Пользователь был разбанен" : "Пользователь был забанен";
                        user.setBun(!user.isBun());
                        userService.Update(user);
                        out.println(gson.toJson(new Response(gson.toJson(message), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case ADD_ROLE_MANAGER: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        User user = userService.FindById(id);
                        if (user == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Пользователь не найден"), ResponseType.NOT_FOUND)));
                            break;
                        }
                        String message;
                        if (user.getRoles().contains(Role.MANAGER)) {
                            user.getRoles().remove(Role.MANAGER);
                            message = "Роль удалена";
                        } else {
                            user.getRoles().add(Role.MANAGER);
                            message = "Добавлена новая роль";
                        }
                        userService.Update(user);
                        out.println(gson.toJson(new Response(gson.toJson(message), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case DELETE_USER: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        if (userService.FindById(id) == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Пользователь не найден"), ResponseType.NOT_FOUND)));
                        } else userService.DeleteById(id);
                        out.println(gson.toJson(new Response(gson.toJson("Пользователь с id " + id + " был удалён"), ResponseType.OK)));
                        out.flush();
                        break;
                    }

                    case UPDATE_ACCOUNT: {
                        User user = new Gson().fromJson(request.getMessage(), User.class);
                        User userDB = userService.FindByLogin(user.getLogin());
                        if (userDB != null && userDB.getId() != user.getId()) {
                            out.println(gson.toJson(new Response(gson.toJson("Данный логин уже используется"), ResponseType.BAD_REQUEST)));
                        } else {
                            userService.Update(user);
                            out.println(gson.toJson(new Response(gson.toJson("Информация успешно обновлена"), ResponseType.OK)));

                        }
                        out.flush();
                        break;
                    }
                    case ADD_PROJECT: {
                        Project project = new Gson().fromJson(request.getMessage(), Project.class);
                        projectService.Save(project);
                        out.println(gson.toJson(new Response(gson.toJson("Новое исследование добавлено"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case GET_ALL_PROJECTS: {
                        out.println(gson.toJson(new Response(gson.toJson(projectService.FindAll()), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case ADD_PATENT: {
                        Patent patent = new Gson().fromJson(request.getMessage(), Patent.class);
                        patentService.Save(patent);
                        out.println(gson.toJson(new Response(gson.toJson(null), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case ADD_EQUIPMENT: {
                        Equipment equipment = new Gson().fromJson(request.getMessage(), Equipment.class);
                        equipmentService.Save(equipment);
                        out.println(gson.toJson(new Response(gson.toJson(null), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case GET_ALL_PATENTS: {
                        out.println(gson.toJson(new Response(gson.toJson(patentService.FindAll()), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case DELETE_PATENT: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        if (patentService.FindById(id) == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Патент не найден"), ResponseType.NOT_FOUND)));
                        } else patentService.DeleteById(id);
                        out.println(gson.toJson(new Response(gson.toJson("Патент с id " + id + " был удалён"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case GET_ALL_EQUIPMENT: {
                        out.println(gson.toJson(new Response(gson.toJson(equipmentService.FindAll()), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case DELETE_EQUIPMENT: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        if (equipmentService.FindById(id) == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Оборудование не найдено"), ResponseType.NOT_FOUND)));
                        } else equipmentService.DeleteById(id);
                        out.println(gson.toJson(new Response(gson.toJson("Оборудование с id " + id + " было удалено"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case UPDATE_PATENT: {
                        Patent patent = new Gson().fromJson(request.getMessage(), Patent.class);
                        patentService.Update(patent);
                        out.println(gson.toJson(new Response(gson.toJson("Информация успешно обновлена"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case UPDATE_EQUIPMENT: {
                        Equipment equipment = new Gson().fromJson(request.getMessage(), Equipment.class);
                        equipmentService.Update(equipment);
                        out.println(gson.toJson(new Response(gson.toJson("Информация успешно обновлена"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case GET_PROJECT_BY_ID: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        Project project = projectService.FindById(id);
                        if (project == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Проект не найден"), ResponseType.NOT_FOUND)));
                        }
                        else {
                            out.println(gson.toJson(new Response(gson.toJson(project), ResponseType.OK)));
                        }
                        out.flush();
                        break;
                    }


                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
