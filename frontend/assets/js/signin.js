
const URL = 'http://localhost:8080/project1/';

let mainWindow = document.getElementById("main-window");
let signInForm = document.getElementById('signInForm');
let signInButton = document.getElementById("sign-in");
let loginPage = document.getElementById("login-page");

//loginPage.onload = checkCredentials;

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function cleanScreen(){
    removeAllChildNodes(mainWindow);

    let loading = document.createElement('img');
    loading.src = 'assets/img/loading.gif';
    loading.id = 'loadImg';
    loading.className = 'center-block';


    mainWindow.appendChild(loading);

    setTimeout(()=>{
        removeAllChildNodes(mainWindow);
        mainWindow.appendChild(signInForm);
    }, 5000);
}



signInButton.addEventListener("click",login,false);

function getUserCreds(){
    let username = document.getElementById("inputUser").value;
    let password = document.getElementById("inputPassword").value;

    if(username !== null && username !== '' && password !== null && password !== '' ){
        let userCreds = {
            username: username,
            user_password: password
        }
        return userCreds;
    }
        return null;
}

async function login(){
    let userCreds = getUserCreds();
    cleanScreen();
    if(userCreds != null){
        let response = await fetch(URL+'login',{
            method:'POST',
            body: JSON.stringify(userCreds),
            credentials: 'include'

        });
        if(response.status === 200){
            console.log('Successfully logged in.');
            let user = await response.json();
            console.log(user);
            // COOKIE for username and usertype
            document.cookie = `username=${user.username}`;
            document.cookie = `user_role=${user.user_role}`;
            console.log(getCookie("user_role"));
            if(getCookie("user_role") == 'employee'){
                window.location.replace("http://localhost:8080/frontend/employee/");
            }
            else if(getCookie("user_role") == 'f_manager'){
                window.location.replace("http://localhost:8080/frontend/manager/");
            }
            
        }
        else{
            console.log('Login unsuccessful');
        }
    }
}







