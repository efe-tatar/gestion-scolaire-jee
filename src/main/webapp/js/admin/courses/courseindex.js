
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/admin/courses");
datatable.setDestination("table-dest-div");
datatable.paginate("studentsPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

export function processTable() {
	datatable.process();
}
window.processTable = processTable;

export function storeCourse() {
	
	console.log("store course");
	
	let form = document.getElementById("courseStoreForm");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/courses",
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
window.storeCourse = storeCourse;

datatable.process();

