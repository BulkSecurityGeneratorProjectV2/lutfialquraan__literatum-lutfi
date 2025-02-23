package controller.actions.user.access;

import at.favre.lib.crypto.bcrypt.BCrypt;
import controller.actions.IAction;
import model.database.DAO;
import model.database.UsersDAO;
import model.users.AbstractBaseUser;
import model.users.BasicUser;
import utilities.ControlSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpAction implements IAction {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String user_name = request.getParameter("user_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        char[] encryptedText = BCrypt.withDefaults().hashToChar(12, password.toCharArray());
        password = new String(encryptedText);

        AbstractBaseUser basicUser = new BasicUser(first_name, last_name, user_name, email, password);
        DAO userDAO = new UsersDAO();
        userDAO.insert(basicUser);

        ControlSession.createSession(request, basicUser);
        response.sendRedirect("/showArticles");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/user/sign-up.jsp");
        requestDispatcher.forward(request, response);

    }
}
