
checkIfUserLoggedIn();

function checkIfUserLoggedIn(){
    if(getCookie("username")){
      if(getCookie("user_role") == 'f_manager'){
          window.location.replace("http://localhost:8080/frontend/manager");
      }
      else if(getCookie("user_role") == 'employee'){
        window.location.replace("http://localhost:8080/frontend/employee");
      }
    }
}
