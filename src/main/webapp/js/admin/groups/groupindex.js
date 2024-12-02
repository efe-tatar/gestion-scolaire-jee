
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/admin/groups");
datatable.setDestination("table-dest-div");
datatable.paginate("studentsPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

export function processTable() {
	datatable.process();
}
window.processTable = processTable;

export function storeGroup() {
	
	let form = document.getElementById("groupStoreForm");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/groups",
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
window.storeGroup = storeGroup;

datatable.process();