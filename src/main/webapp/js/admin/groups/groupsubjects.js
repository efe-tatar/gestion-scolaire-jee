
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("subject-table-filter");
datatable.setRequestUrl("/ENT/data/admin/subjects");
datatable.setDestination("subject-dest");
datatable.paginate("subjectPaginator");

document.getElementById('subject-table-filter').addEventListener('input', function() {
    datatable.process();
});

export function assignTeacher() {
	let form = document.getElementById("teacherAppointmentForm");
	let formData = new FormData(form);
	
	fetch(
            "/ENT/data/admin/groupsubjects",
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
window.assignTeacher = assignTeacher;


export function processSubjectTable() {
	datatable.process();
}
window.processSubjectTable = processSubjectTable;

datatable.process();