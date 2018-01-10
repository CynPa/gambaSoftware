/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.Titulo;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTitulo;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class TituloBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioTitulo servicioTitulo;
/*  30:    */   private Titulo titulo;
/*  31:    */   private LazyDataModel<Titulo> listaTitulo;
/*  32:    */   private DataTable dtTitulo;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 75 */     this.listaTitulo = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<Titulo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 82 */         List<Titulo> lista = new ArrayList();
/*  44: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 85 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 86 */         lista = TituloBean.this.servicioTitulo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 88 */         TituloBean.this.listaTitulo.setRowCount(TituloBean.this.servicioTitulo.contarPorCriterio(filters));
/*  50:    */         
/*  51: 90 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearTitulo()
/*  57:    */   {
/*  58:104 */     this.titulo = new Titulo();
/*  59:105 */     this.titulo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  60:106 */     this.titulo.setIdSucursal(AppUtil.getSucursal().getId());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:115 */     if ((getTitulo() != null) && (getTitulo().getIdTitulo() != 0)) {
/*  66:116 */       setEditado(true);
/*  67:    */     } else {
/*  68:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:120 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:130 */       this.servicioTitulo.guardar(this.titulo);
/*  78:131 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:132 */       setEditado(false);
/*  80:133 */       limpiar();
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:136 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:138 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:148 */       this.servicioTitulo.eliminar(this.titulo);
/*  95:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:152 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:154 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:163 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:172 */     crearTitulo();
/* 113:173 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Titulo getTitulo()
/* 117:    */   {
/* 118:190 */     return this.titulo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTitulo(Titulo titulo)
/* 122:    */   {
/* 123:200 */     this.titulo = titulo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<Titulo> getListaTitulo()
/* 127:    */   {
/* 128:209 */     return this.listaTitulo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaTitulo(LazyDataModel<Titulo> listaTitulo)
/* 132:    */   {
/* 133:219 */     this.listaTitulo = listaTitulo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtTitulo()
/* 137:    */   {
/* 138:228 */     return this.dtTitulo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtTitulo(DataTable dtTitulo)
/* 142:    */   {
/* 143:238 */     this.dtTitulo = dtTitulo;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.TituloBean
 * JD-Core Version:    0.7.0.1
 */