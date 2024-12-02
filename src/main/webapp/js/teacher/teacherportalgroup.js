
import TDataTable from "./../tdatatable/tdatatable.js";

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
    
datatable.setRequestUrl("/ENT/data/teacher/groups");
datatable.setDestination("table-dest-div");
datatable.paginate("studentsPaginator");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

export function processStudentTable() {
	datatable.process();
}
window.processStudentTable = processStudentTable;

datatable.process();

