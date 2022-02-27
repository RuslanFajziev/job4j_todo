function checkState() {
    let chkBox = document.getElementById('all');
    if (chkBox.checked) {
        getItemsTodo('true');
    } else {
        getItemsTodo('false');
    }
}

function postStateItem(val) {
    let index = 'input[id="' + val + '"]';
    let value = document.querySelector(index).value;
    let id = val;
    let url = 'http://localhost:8080/job4j_todo/todo.date?id=' + id + '&description=' + value;
    $.ajax({
        type: 'PUT',
        url: url,
    }).done(function (data) {
        checkState();
    });
}

function getСategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/categoryJson.do',
        dataType: 'json'
    }).done(function (data) {
        let result = "";
        // result += "<option disabled selected>Выберите город</option>";
        for (var сategoryItem of data) {
            result += "<option value=" + сategoryItem.id + " checked>" + сategoryItem.name + "</option>";
        }
        $('#categoryItem').html(result);
    });
}

function getItemsTodo(flag) {
    getСategories();
    let url = 'http://localhost:8080/job4j_todo/todo?flag=' + flag;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json'
    }).done(function (data) {
        let result = "";

        if (flag) {
            for (var item of data) {
                let rsl = item.done;
                let inputValue = item.description;
                let inputId = item.id;
                let endStr;

                if (rsl) {
                    endStr = " disabled checked>";
                }

                if (!rsl) {
                    endStr = ">";
                }
                result += '<tr><td>' + item.description + '</td>' +
                    '<td><input class="form-check-input" type="checkbox" id=' + inputId
                    + ' value=' + inputValue
                    + ' onchange="postStateItem(this.id)"'
                    + endStr + '</td></tr>';
            }
        }

        if (!flag) {
            let inputValue = item.id;
            let inputId = "task" + inputValue;
            result += '<tr><td>' + item.description + '</td>' +
                '<td><input class="form-check-input" type="checkbox" id=' + inputId + '</td></tr>';
        }

        $('#listItems').html(result);
    })
}