const URL = 'http://localhost:8080/project1/';
checkIfEmployeeLoggedIn();

function checkIfEmployeeLoggedIn(){
    if(getCookie("username")){
      if(getCookie("user_role") == 'f_manager'){
          window.location.replace("http://localhost:8080/frontend/manager");
      }
    }
    else{
      window.location.replace("http://localhost:8080/frontend/");
    }
}

async function getReimbursements(){
    console.log(URL+'reimbursement/'+getCookie("username"));
    
    let response =  await fetch(URL+'reimbursement/'+getCookie("username"));

    if(response.status === 200){
        let data = await response.json();
        console.log(data);
        return data;
    }
    else{
        console.log("There was a problem accessing user reimbursements");
    }
}