
import TDataTable from "./../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("filter-form");
datatable.setRequestUrl("/ENT/data/teacher/groups");
datatable.setDestination("table-dest-div");

document.getElementById('filter-form').addEventListener('input', function() {
    datatable.process();
});

datatable.process();