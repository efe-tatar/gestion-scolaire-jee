
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/admin/subjects");
datatable.setDestination("table-dest-div");
datatable.paginate("subjectPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

export function processSubjectTable() {
	datatable.process();
}
window.processSubjectTable = processSubjectTable;

export function storeSubject() {
	
	let form = document.getElementById("subjectStoreForm");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/subjects",
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
window.storeSubject = storeSubject;

datatable.process();