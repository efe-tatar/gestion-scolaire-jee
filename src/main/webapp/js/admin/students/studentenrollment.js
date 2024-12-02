
import TDataTable from "./../../tdatatable/tdatatable.js";

let eTable = new TDataTable();

eTable.setDestination("enrollment-list-dest");
eTable.setFormId("enrollments-form");
eTable.setRequestUrl("/ENT/data/admin/enrollments");

export function storeEnrollment() {
	
	console.log("store enrollment");
	
	let form = document.getElementById("enrollment-save-form");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/enrollments",
            {
                method: "POST",
                body: formData
            }
        ).then(
            response => {
                if(!response.ok) throw new Error("Internal Error");
                return response.json();
            }
        ).then(
            data => {
				if(data.success) {
					eTable.process();
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
window.storeEnrollment = storeEnrollment;

eTable.process();

