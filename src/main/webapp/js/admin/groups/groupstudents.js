
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("student-table-filters");
datatable.setRequestUrl("/ENT/data/admin/students");
datatable.setDestination("student-dest");
datatable.paginate("studentsPaginator");

document.getElementById('student-table-filters').addEventListener('input', function() {
    datatable.process();
});

datatable.process();

export function processStudentTable() {
	datatable.process();
}
window.processStudentTable = processStudentTable;