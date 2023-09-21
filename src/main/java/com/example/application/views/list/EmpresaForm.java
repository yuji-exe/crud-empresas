package com.example.application.views.list;

import com.example.application.data.entity.Empresa;
import com.example.application.data.entity.TipoEmpresa;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class EmpresaForm extends FormLayout {
    Binder<Empresa> binder = new BeanValidationBinder<>(Empresa.class);
    TextField nomeFantasia = new TextField("Nome fantasia");
    TextField razaoSocial = new TextField("Razao Social");
    TextField cnpj = new TextField("CNPJ");
    DatePicker dataFundacao = new DatePicker("Data fundação");
    @PropertyId("ramoAtividade.descricao")
    TextField ramoAtividade = new TextField("Ramo atividade");
    ComboBox<TipoEmpresa> tipo= new ComboBox<>("Tipo");

    Button save = new Button("Salvar");
    Button delete = new Button("Deletar");
    Button cancel = new Button("Cancelar");
    private Empresa empresa;

    public EmpresaForm(){
        addClassName("empresa-form");
        binder.bind(ramoAtividade, "ramoAtividade.descricao");
        binder.bindInstanceFields(this);

        tipo.setItems(TipoEmpresa.values());
        tipo.setItemLabelGenerator(TipoEmpresa::getDescricao);

        add(
                nomeFantasia,
                razaoSocial,
                cnpj,
                dataFundacao,
                ramoAtividade,
                tipo,
                createButtonsLayout()
        );

    }

    public void setEmpresa(Empresa empresa){
        this.empresa = empresa;
        binder.readBean(empresa);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validadeAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, empresa)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validadeAndSave() {
        try{
            binder.writeBean(empresa);
            fireEvent(new SaveEvent(this, empresa));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class EmpresaFormEvent extends ComponentEvent<EmpresaForm> {
        private Empresa empresa;

        protected EmpresaFormEvent(EmpresaForm source, Empresa empresa) {
            super(source, false);
            this.empresa = empresa;
        }

        public Empresa getEmpresa() {
            return empresa;
        }
    }

    public static class SaveEvent extends EmpresaFormEvent {
        SaveEvent(EmpresaForm source, Empresa empresa) {
            super(source, empresa);
        }
    }

    public static class DeleteEvent extends EmpresaFormEvent {
        DeleteEvent(EmpresaForm source, Empresa empresa) {
            super(source, empresa);
        }

    }

    public static class CloseEvent extends EmpresaFormEvent {
        CloseEvent(EmpresaForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
