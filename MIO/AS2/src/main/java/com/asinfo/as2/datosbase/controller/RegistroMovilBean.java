/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.RegistroMovil;
/*   6:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.annotation.PostConstruct;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class RegistroMovilBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @EJB
/*  25:    */   private ServicioGenerico<RegistroMovil> servicioRegistroMovil;
/*  26:    */   private RegistroMovil registroMovil;
/*  27:    */   private LazyDataModel<RegistroMovil> listaRegistroMovil;
/*  28:    */   private DataTable dtRegistroMovil;
/*  29:    */   
/*  30:    */   @PostConstruct
/*  31:    */   public void init()
/*  32:    */   {
/*  33: 57 */     this.listaRegistroMovil = new LazyDataModel()
/*  34:    */     {
/*  35:    */       private static final long serialVersionUID = 1L;
/*  36:    */       
/*  37:    */       public List<RegistroMovil> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  38:    */       {
/*  39: 64 */         List<RegistroMovil> lista = new ArrayList();
/*  40: 65 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  41:    */         
/*  42: 67 */         lista = RegistroMovilBean.this.servicioRegistroMovil.obtenerListaPorPagina(RegistroMovil.class, startIndex, pageSize, sortField, ordenar, filters);
/*  43: 68 */         RegistroMovilBean.this.listaRegistroMovil.setRowCount(RegistroMovilBean.this.servicioRegistroMovil.contarPorCriterio(RegistroMovil.class, filters));
/*  44:    */         
/*  45: 70 */         return lista;
/*  46:    */       }
/*  47:    */     };
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String editar()
/*  51:    */   {
/*  52: 82 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  53: 83 */     return "";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String guardar()
/*  57:    */   {
/*  58: 93 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  59: 94 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String crear()
/*  63:    */   {
/*  64: 98 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  65: 99 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String limpiar()
/*  69:    */   {
/*  70:109 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String eliminar()
/*  74:    */   {
/*  75:119 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  76:120 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public RegistroMovil getRegistroMovil()
/*  80:    */   {
/*  81:129 */     return this.registroMovil;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setRegistroMovil(RegistroMovil registroMovil)
/*  85:    */   {
/*  86:139 */     this.registroMovil = registroMovil;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public LazyDataModel<RegistroMovil> getListaRegistroMovil()
/*  90:    */   {
/*  91:148 */     return this.listaRegistroMovil;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setListaRegistroMovil(LazyDataModel<RegistroMovil> listaRegistroMovil)
/*  95:    */   {
/*  96:158 */     this.listaRegistroMovil = listaRegistroMovil;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public DataTable getDtRegistroMovil()
/* 100:    */   {
/* 101:167 */     return this.dtRegistroMovil;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDtRegistroMovil(DataTable dtRegistroMovil)
/* 105:    */   {
/* 106:177 */     this.dtRegistroMovil = dtRegistroMovil;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:183 */     return null;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.RegistroMovilBean
 * JD-Core Version:    0.7.0.1
 */