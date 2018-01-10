/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CargaPreviaTransportista;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class CargaPreviaTransportistaBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -8204372454439685260L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<CargaPreviaTransportista> servicioCargaPreviaTransportista;
/*  32:    */   private CargaPreviaTransportista cargaPreviaTransportista;
/*  33:    */   private LazyDataModel<CargaPreviaTransportista> listaCargaPreviaTransportista;
/*  34:    */   private DataTable dtCargaPreviaTransportista;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 52 */     this.listaCargaPreviaTransportista = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<CargaPreviaTransportista> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 60 */         List<CargaPreviaTransportista> lista = new ArrayList();
/*  46: 61 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 63 */         CargaPreviaTransportistaBean.this.agregarFiltroOrganizacion(filters);
/*  49:    */         
/*  50: 65 */         lista = CargaPreviaTransportistaBean.this.servicioCargaPreviaTransportista.obtenerListaPorPagina(CargaPreviaTransportista.class, startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         
/*  52: 67 */         CargaPreviaTransportistaBean.this.listaCargaPreviaTransportista
/*  53: 68 */           .setRowCount(CargaPreviaTransportistaBean.this.servicioCargaPreviaTransportista.contarPorCriterio(CargaPreviaTransportista.class, filters));
/*  54:    */         
/*  55: 70 */         return lista;
/*  56:    */       }
/*  57:    */     };
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String editar()
/*  61:    */   {
/*  62: 78 */     if ((this.cargaPreviaTransportista != null) && (this.cargaPreviaTransportista.getId() != 0)) {
/*  63: 79 */       setEditado(true);
/*  64:    */     } else {
/*  65: 81 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67: 83 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 89 */       this.servicioCargaPreviaTransportista.guardarValidar(getCargaPreviaTransportista());
/*  75: 90 */       limpiar();
/*  76: 91 */       setEditado(false);
/*  77: 92 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:    */     }
/*  79:    */     catch (AS2Exception e)
/*  80:    */     {
/*  81: 94 */       JsfUtil.addErrorMessage(e, "");
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85: 96 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86: 97 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88: 99 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:104 */     if ((this.cargaPreviaTransportista != null) && (this.cargaPreviaTransportista.getId() != 0)) {
/*  94:    */       try
/*  95:    */       {
/*  96:106 */         this.servicioCargaPreviaTransportista.eliminar(this.cargaPreviaTransportista);
/*  97:107 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  98:108 */         limpiar();
/*  99:    */       }
/* 100:    */       catch (Exception e)
/* 101:    */       {
/* 102:110 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:111 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */       }
/* 105:    */     } else {
/* 106:114 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 107:    */     }
/* 108:116 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:121 */     crearCargaPreviaTransportista();
/* 114:122 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String cargarDatos()
/* 118:    */   {
/* 119:127 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void crearCargaPreviaTransportista()
/* 123:    */   {
/* 124:131 */     this.cargaPreviaTransportista = new CargaPreviaTransportista();
/* 125:132 */     this.cargaPreviaTransportista.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 126:133 */     this.cargaPreviaTransportista.setIdSucursal(AppUtil.getSucursal().getId());
/* 127:134 */     this.cargaPreviaTransportista.setActivo(true);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public CargaPreviaTransportista getCargaPreviaTransportista()
/* 131:    */   {
/* 132:138 */     return this.cargaPreviaTransportista;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setCargaPreviaTransportista(CargaPreviaTransportista cargaPreviaTransportista)
/* 136:    */   {
/* 137:142 */     this.cargaPreviaTransportista = cargaPreviaTransportista;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public LazyDataModel<CargaPreviaTransportista> getListaCargaPreviaTransportista()
/* 141:    */   {
/* 142:146 */     return this.listaCargaPreviaTransportista;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaCargaPreviaTransportista(LazyDataModel<CargaPreviaTransportista> listaCargaPreviaTransportista)
/* 146:    */   {
/* 147:150 */     this.listaCargaPreviaTransportista = listaCargaPreviaTransportista;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public DataTable getDtCargaPreviaTransportista()
/* 151:    */   {
/* 152:154 */     return this.dtCargaPreviaTransportista;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDtCargaPreviaTransportista(DataTable dtCargaPreviaTransportista)
/* 156:    */   {
/* 157:158 */     this.dtCargaPreviaTransportista = dtCargaPreviaTransportista;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.CargaPreviaTransportistaBean
 * JD-Core Version:    0.7.0.1
 */