<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Список дел</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="todo.js"></script>
<script>
    <c:if test="${user != null}">
    $(document).ready(getItemsTodo('false'));
    </c:if>
</script>
<body>
<div class="container">
    <div class="card-body">
        <div class="row">
            <c:if test="${user == null}">

                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>

                <a class="nav-link" href="<%=request.getContextPath()%>/reg.jsp">Регистрация</a>

            </c:if>
        </div>
        <c:if test="${user != null}">
            <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти из <c:out
                    value="${user.email}"/></a>
            <div class="row">
                <form id="my_form" action="<%=request.getContextPath()%>/todo.date" method="post">
                    <div class="form-group">
                        <label for="description">Описание задачи</label>
                        <input required type="text" class="form-control" id="description" name="description"
                               placeholder="Описание задачи">
                    </div>
                    <label for="categoryItem">Категории</label>
                    <select class="form-control" id="categoryItem" name="categoryItem" multiple></select>
                    <button type="submit" class="btn btn-success">Добавить задачу</button>
                </form>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="all" id="all" onchange="checkState()">
                <label class="form-check-label" for="all">
                    Show all
                </label>
            </div>
        </c:if>
        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Описание задачи</th>
                    <th scope="col">Флаг выполнения</th>
                </tr>
                </thead>
                <tbody id="listItems">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>