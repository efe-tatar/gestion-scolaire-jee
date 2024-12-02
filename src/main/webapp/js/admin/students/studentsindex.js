
import TDataTable from "./../../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/admin/students");
datatable.setDestination("table-dest-div");
datatable.paginate("studentsPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

datatable.process();

export function processStudentTable() {
	datatable.process();
}
window.processStudentTable = processStudentTable;