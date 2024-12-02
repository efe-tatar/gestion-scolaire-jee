
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("groups-filter-form");
datatable.setRequestUrl("/ENT/data/admin/groups");
datatable.setDestination("groups-list-dest");
datatable.paginate("studentsPaginator");

document.getElementById('groups-filter-form').addEventListener('input', function() {
    datatable.process();
});

export function addStudent() {
	let form = document.getElementById("studentaddform");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/groupstudents",
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
					datatable.process();
	                form.reset();				
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
window.addStudent = addStudent;

datatable.process();