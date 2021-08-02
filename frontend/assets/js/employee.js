
let page = document.getElementById("content");
let userMsg = document.getElementById("welcome-msg");
let logoutButton = document.getElementById("logout-button");
let requestForm = document.getElementById("request-form");
let refreshButton = document.getElementById("refresh-button");
refreshButton.onclick = populateReimbTable;
requestForm.addEventListener('submit', addReimbursement);
logoutButton.onclick = logout;

//Set welcome message
userMsg.innerHTML = `Welcome ${getCookie("username")}`;

//Populate reimbursemnts table
populateReimbTable();

function logout(){
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/frontend;";
    document.cookie = "user_role=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/frontend;";
    window.location.replace("http://localhost:8080/frontend/");
}



async function populateReimbTable(){
    let reimbursements = await getReimbursements();
    let reimbTable = document.getElementById("reimbTable");

    reimbTable.innerHTML = "";
    let row = document.createElement('tr');
    for(let reimb of reimbursements){

        let row = document.createElement('tr');
    
        for(let cell in reimb){
            if(reimb[cell] == getCookie("username")){
                continue;
            }
            let td = document.createElement('td');
            td.innerText=reimb[cell]; 
            row.appendChild(td);
        }
        reimbTable.appendChild(row);
    }
}

function getNewReimbursement(){
    let author = getCookie("username");
    let amount = document.getElementById("requested-amount").value;
    let description = document.getElementById("request-description").value;
    let request_type = document.getElementById("request-type").value;

    let reimbursement = {
        amount: amount,
        description: description,
        author: author,
        reimb_type: request_type
    }

    return reimbursement;
}

async function addReimbursement(event){
    event.preventDefault();
    let reimbursement = getNewReimbursement();

    let response = await fetch(URL+'reimbursement', {
        method:'POST',
        body: JSON.stringify(reimbursement),
        credentials: 'include'
    });

    if(response.status === 201){
        console.log('Successfully created a new reimbursement.');
        document.getElementById("requested-amount").value = 0;
        document.getElementById("request-description").value = "";
        document.getElementById("request-type").value = "";
        populateReimbTable();
    }else{
        console.log("Something went wrong tring to add new home.")
    }



}



//page.onload = getReimbursements;


//async function getReimbursements();

