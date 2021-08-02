let logoutButton = document.getElementById("logout-button");
logoutButton.onclick = logout;

//Set welcome message
let userMsg = document.getElementById("welcome-msg");
userMsg.innerHTML = `Welcome ${getCookie("username")}`;



//Populate reimbursemnts table
populateReimbTable();


function logout(){
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/frontend;";
    document.cookie = "user_role=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/frontend;";
    window.location.replace("http://localhost:8080/frontend/");
}

function filterTable(){
    let status_filter = document.getElementById("filterBy").value;
    let reimb_msg = document.getElementById("reimb-msg");
    switch(status_filter){
        case "all":
            populateReimbTable();
            reimb_msg.innerText = "All Reimbursements";
            break;
        case "pending":
            populatePendingReimbTable();
            reimb_msg.innerText = "Pending Reimbursements";
            break;
        case "approved":
            populateApprovedReimbTable();
            reimb_msg.innerText = "Approved Reimbursements";
            break;
        case "denied":
            populateDeniedReimbTable();
            reimb_msg.innerText = "Denied Reimbursements";
            break;
    }
}

function populatePendingHead(){
    let tableHead = document.getElementById("tableHead");

     //Create table head for pending reimbursements
     removeAllChildNodes(tableHead);
    
     let idHead = document.createElement("th");
     idHead.scope="col-1";
     idHead.innerText = "ID";
     let amountHead = document.createElement("th");
     amountHead.scope="col-1";
     amountHead.innerText = "Amount";
     let submittedHead = document.createElement("th");
     submittedHead.scope="col-1";
     submittedHead.innerText = "Date Submitted";
     let descriptionHead = document.createElement("th");
     descriptionHead.scope="col-4";
     descriptionHead.innerText = "Description";
     let authorHead = document.createElement("th");
     authorHead.scope="col-1";
     authorHead.innerText = "Author";
     let statusHead = document.createElement("th");
     statusHead.scope="col-1";
     statusHead.innerText = "Status";
     let typeHead = document.createElement("th");
     typeHead.scope="col-1";
     typeHead.innerText = "Request Type";
     let actionHead = document.createElement("th");
     actionHead.scope="col-2";
     actionHead.innerText = "Actions";

     tableHead.appendChild(idHead);
     tableHead.appendChild(amountHead);
     tableHead.appendChild(submittedHead);
     tableHead.appendChild(descriptionHead);
     tableHead.appendChild(authorHead);
     tableHead.appendChild(statusHead);
     tableHead.appendChild(typeHead);
     tableHead.appendChild(actionHead);
}

function populateAllHead(){
    let tableHead = document.getElementById("tableHead");

     //Create table head for pending reimbursements
     removeAllChildNodes(tableHead);
    
     let idHead = document.createElement("th");
     idHead.scope="col-1";
     idHead.innerText = "ID";
     let amountHead = document.createElement("th");
     amountHead.scope="col-1";
     amountHead.innerText = "Amount";
     let submittedHead = document.createElement("th");
     submittedHead.scope="col-1";
     submittedHead.innerText = "Date Submitted";
     let resolvedHead = document.createElement("th");
     resolvedHead.scope="col-1";
     resolvedHead.innerText = "Date Resolved";
     let descriptionHead = document.createElement("th");
     descriptionHead.scope="col-2";
     descriptionHead.innerText = "Description";
     let authorHead = document.createElement("th");
     authorHead.scope="col-1";
     authorHead.innerText = "Author";
     let statusHead = document.createElement("th");
     statusHead.scope="col-1";
     statusHead.innerText = "Status";
     let typeHead = document.createElement("th");
     typeHead.scope="col-1";
     typeHead.innerText = "Request Type";
     let resolverHead = document.createElement("th");
     resolverHead.scope="col-1";
     resolverHead.innerText = "Resolver";
     let actionHead = document.createElement("th");
     actionHead.scope="col-2";
     actionHead.innerText = "Actions";

     tableHead.appendChild(idHead);
     tableHead.appendChild(amountHead);
     tableHead.appendChild(submittedHead);
     tableHead.appendChild(resolvedHead);
     tableHead.appendChild(descriptionHead);
     tableHead.appendChild(authorHead);
     tableHead.appendChild(statusHead);
     tableHead.appendChild(typeHead);
     tableHead.appendChild(resolverHead);
     tableHead.appendChild(actionHead);
}


async function populatePendingReimbTable(){
    let reimbursements = await getReimbursements();
    let reimbTable = document.getElementById("reimbTable");

    populateAllHead();

    reimbTable.innerHTML = "";
    let row = document.createElement('tr');
    console.log()
    for(let reimb of reimbursements){
        if(reimb["status"] != "pending"){
            continue;
        }

        let row = document.createElement('tr');
    
        for(let cell in reimb){
            let td = document.createElement('td');
            td.id = `${cell}-${reimb["reimb_id"]}`;
            td.innerText=reimb[cell]; 
            row.appendChild(td);
        }

        if(reimb["status"] == "pending"){
            let td = document.createElement('td');
            td.id = `actions-${reimb["reimb_id"]}`;
            let acceptButton = document.createElement('button');
            let denyButton = document.createElement('button');
            acceptButton.innerText = "Approve";
            acceptButton.value = reimb["reimb_id"];
            acceptButton.id = "approveButton";
            acceptButton.className = "btn btn-success";
            denyButton.innerText = "Deny";
            denyButton.value = reimb["reimb_id"];
            denyButton.id = "denyButton";
            denyButton.className = "btn btn-danger";
            acceptButton.addEventListener('click', approveRequest);
            denyButton.addEventListener('click', denyRequest);
            td.appendChild(acceptButton);
            td.append(" ");
            td.appendChild(denyButton);
            row.appendChild(td);
        }
        reimbTable.appendChild(row);
    }
}

async function populateReimbTable(){
    let reimbursements = await getReimbursements();
    let reimbTable = document.getElementById("reimbTable");

    populateAllHead();

    reimbTable.innerHTML = "";
    let row = document.createElement('tr');
    console.log()
    for(let reimb of reimbursements){

        let row = document.createElement('tr');
    
        for(let cell in reimb){
            let td = document.createElement('td');
            td.id = `${cell}-${reimb["reimb_id"]}`;
            td.innerText=reimb[cell]; 
            row.appendChild(td);
        }

        if(reimb["status"] == "pending"){
            let td = document.createElement('td');
            td.id = `actions-${reimb["reimb_id"]}`;
            let acceptButton = document.createElement('button');
            let denyButton = document.createElement('button');
            acceptButton.innerText = "Approve";
            acceptButton.value = reimb["reimb_id"];
            acceptButton.id = "approveButton";
            acceptButton.className = "btn btn-success";
            denyButton.innerText = "Deny";
            denyButton.value = reimb["reimb_id"];
            denyButton.id = "denyButton";
            denyButton.className = "btn btn-danger";
            acceptButton.addEventListener('click', approveRequest);
            denyButton.addEventListener('click', denyRequest);
            td.appendChild(acceptButton);
            td.append(" ");
            td.appendChild(denyButton);
            row.appendChild(td);
        }else{
            let td = document.createElement('td');
            if(reimb["status"] == "denied"){
                td.className = "text-danger";
            }else{
                td.className = "text-success";
            }
            
            td.innerText = reimb["status"];
            row.appendChild(td);
        }
        reimbTable.appendChild(row);
    }
}

async function populateApprovedReimbTable(){
    let reimbursements = await getReimbursements();
    let reimbTable = document.getElementById("reimbTable");

    populateAllHead();

    reimbTable.innerHTML = "";
    let row = document.createElement('tr');
    for(let reimb of reimbursements){
        if(reimb["status"] != "approved"){
            continue;
        }
        let row = document.createElement('tr');
    
        for(let cell in reimb){
            let td = document.createElement('td');
            td.id = `${cell}-${reimb["reimb_id"]}`;
            td.innerText=reimb[cell]; 
            row.appendChild(td);
        }

        let td = document.createElement('td');
            
        td.className = "text-success";
            
        td.innerText = reimb["status"];
        row.appendChild(td);
        
        reimbTable.appendChild(row);
    }
}

async function populateDeniedReimbTable(){
    let reimbursements = await getReimbursements();
    let reimbTable = document.getElementById("reimbTable");

    populateAllHead();

    reimbTable.innerHTML = "";
    let row = document.createElement('tr');
    for(let reimb of reimbursements){
        if(reimb["status"] != "denied"){
            continue;
        }
        let row = document.createElement('tr');
    
        for(let cell in reimb){
            let td = document.createElement('td');
            td.id = `${cell}-${reimb["reimb_id"]}`;
            td.innerText=reimb[cell]; 
            row.appendChild(td);
        }

        let td = document.createElement('td');
            
        td.className = "text-danger";
            
        td.innerText = reimb["status"];
        row.appendChild(td);
        
        reimbTable.appendChild(row);
    }
}

async function approveRequest(event){
    
    let id = event.target.value;
    let status = "approved";
    let resolver = getCookie("username")

    let reimbursement = {
        reimb_id: id,
        status: status,
        resolver: resolver
    }

    let response = await fetch(URL+'reimbursement/'+id, {
        method:'PUT',
        body: JSON.stringify(reimbursement),
        credentials: 'include'
    });

    if(response.status === 200){
        console.log('reimbursement '+event.target.value+' approved' );
        let resolved = document.getElementById(`resolved-${id}`);
        let today = new Date();
        resolved.innerText = `${today.getFullYear()+"-"+ ('0'+(today.getMonth()+1)).slice(-2) +"-"+('0'+today.getDate()).slice(-2)}`;
        let statusField = document.getElementById(`status-${id}`);
        statusField.innerText = `${status}`;
        let resolverField = document.getElementById(`resolver-${id}`);
        resolverField.innerText = `${resolver}`;

        let actionsField = document.getElementById(`actions-${id}`);
        actionsField.className = "text-success";
        actionsField.innerHTML = `${status}`;

    }else{
        console.log("Something went wrong trying to update status.")
    }
}
async function denyRequest(event){    
    let id = event.target.value;
    let status = "denied";
    let resolver = getCookie("username")

    let reimbursement = {
        reimb_id: id,
        status: status,
        resolver: resolver
    }

    let response = await fetch(URL+'reimbursement/'+id, {
        method:'PUT',
        body: JSON.stringify(reimbursement),
        credentials: 'include'
    });

    if(response.status === 200){
        console.log('reimbursement '+event.target.value+' denied' );
        let resolved = document.getElementById(`resolved-${id}`);
        let today = new Date();
        resolved.innerText = `${today.getFullYear()+"-"+ ('0'+(today.getMonth()+1)).slice(-2) +"-"+('0'+today.getDate()).slice(-2)}`;
        let statusField = document.getElementById(`status-${id}`);
        statusField.innerText = `${status}`;
        let resolverField = document.getElementById(`resolver-${id}`);
        resolverField.innerText = `${resolver}`;

        let actionsField = document.getElementById(`actions-${id}`);
        actionsField.className = "text-danger";
        actionsField.innerHTML = `${status}`;

    }else{
        console.log("Something went wrong trying to update status.")
    }
    
}