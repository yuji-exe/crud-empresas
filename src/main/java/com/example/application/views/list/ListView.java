package com.example.application.views.list;

import com.example.application.data.entity.Empresa;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Collections;

@PageTitle("Empresas | EnterpriseEcho")
@Route(value = "crm", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {
    Grid<Empresa> grid = new Grid<>(Empresa.class);
    TextField filterText = new TextField();
    EmpresaForm form;
    private CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setEmpresa(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllEmpresas(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new EmpresaForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveEmpresa);
        form.addDeleteListener(this::deleteEmpresa);
        form.addCloseListener(e -> closeEditor());
    }

    private void deleteEmpresa(EmpresaForm.DeleteEvent event) {
        service.deleteEmpresa(event.getEmpresa());
        updateList();
        closeEditor();
    }

    private void saveEmpresa(EmpresaForm.SaveEvent event){
        service.saveEmpresa(event.getEmpresa());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filtrar por nome...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmpresaButton = new Button("Adicionar empresa");
        addEmpresaButton.addClickListener(click -> addEmpresa());

        //addEmpresaButton depois
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmpresaButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addEmpresa() {
        grid.asSingleSelect().clear();
        try{
            editEmpresa(new Empresa());
        } catch (NullPointerException n){

        }
    }

    private void configureGrid() {
        grid.addClassName("empresa-grid");
        grid.setSizeFull();
        grid.setColumns("nomeFantasia", "razaoSocial", "cnpj");
        grid.addColumn(empresa -> empresa.getRamoAtividade().getDescricao()).setHeader("Ramo Atividade");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editEmpresa(e.getValue()));
    }

    private void editEmpresa(Empresa empresa) {
        if(empresa == null){
            closeEditor();
        } else {
            form.setEmpresa(empresa);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
