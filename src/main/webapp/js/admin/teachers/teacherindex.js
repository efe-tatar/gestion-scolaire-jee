
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/admin/teachers");
datatable.setDestination("table-dest-div");
datatable.paginate("studentsPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

export function processTable() {
	datatable.process();
}
window.processTable = processTable;

export function storeTeacher() {
	
	let form = document.getElementById("teacherStoreForm");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/teachers",
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
window.storeTeacher = storeTeacher;

datatable.process();

