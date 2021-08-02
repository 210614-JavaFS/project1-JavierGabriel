const URL = 'http://localhost:8080/project1/';
checkIfAdminLoggedIn();

function checkIfAdminLoggedIn(){
    if(getCookie("username")){
      if(getCookie("user_role") == 'employee'){
          window.location.replace("http://localhost:8080/frontend/employee");
      }
    }
    else{
      window.location.replace("http://localhost:8080/frontend/");
    }
}

function removeAllChildNodes(parent) {
  while (parent.firstChild) {
      parent.removeChild(parent.firstChild);
  }
}

async function getReimbursements(){
    console.log(URL+'reimbursement/'+getCookie("username"));
    
    let response =  await fetch(URL+'reimbursement/');

    if(response.status === 200){
        let data = await response.json();
        console.log(data);
        return data;
    }
    else{
        console.log("There was a problem accessing user reimbursements");
    }
}