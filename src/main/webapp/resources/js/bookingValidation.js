function validateMaster() {
    var master = document.getElementById('masterDropDownListId').value;
    if(master == 0) {
        document.getElementById("invalid-master").innerHTML = "please, choose master";
        return false;
    }
    document.getElementById("invalid-master").innerHTML = "";
    return true;
}

function validateDate() {
    var date = document.getElementById("datepicker").value;
    if(date == "") {
        document.getElementById("invalid-date").innerHTML = "please, choose date";
        return false;
    }
    document.getElementById("invalid-date").innerHTML = "";
    return true;
}

function validateTime() {
    var time = document.getElementById('timeDropDownListId').value;
    if(time == 0) {
        document.getElementById("invalid-time").innerHTML = "please, choose time";
        return false;
    }
    document.getElementById("invalid-time").innerHTML = "";
    return true;
}

function validateForm() {
    if (validateMaster() && validateDate() && validateTime())
        return true;
    else
        return false;
}