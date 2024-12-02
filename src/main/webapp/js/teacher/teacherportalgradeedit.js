
import TDataTable from "./../tdatatable/tdatatable.js";

// get form
let datatable = new TDataTable();

datatable.setFormId("filter-form");

let urlParams = new URLSearchParams(window.location.search);
let groupId = urlParams.get('id');
const form = document.getElementById('filter-form');

const idParam = document.createElement('input');
idParam.type = 'hidden';
idParam.name = 'id';
idParam.value = groupId;
form.appendChild(idParam);

const typeParam = document.createElement("input");
typeParam.type = "hidden";
typeParam.name = "type";
typeParam.value = "edit";
form.appendChild(typeParam);
    
datatable.setRequestUrl("/ENT/data/teacher/groups");
datatable.setDestination("table-dest-div");

export function saveButtonOnClick() {
    const formData = new FormData(document.getElementById("gradeForm"));
    const searchParams = new URLSearchParams();
    for (const [key, value] of formData.entries()) {
        searchParams.append(key, value);
    }
    const data = searchParams.toString();
    
	fetch(
        "/ENT/data/teacher/groups",
        {
			method: "POST",
			body: data,
			headers: {
            	"Content-Type": "application/x-www-form-urlencoded"
        	}
		}
    ).then(
        response => {
            if(!response.ok) throw new Error(response.json().table);
            return response.json();
        }
    ).then(
        data => {
				if(data.success) {
					alert("saved");			
				} else {
					alert(data.errors);
				}
            }
    ).catch(
        error => {
            alert(error);
        }
    );
}
window.saveButtonOnClick = saveButtonOnClick;

datatable.process();
