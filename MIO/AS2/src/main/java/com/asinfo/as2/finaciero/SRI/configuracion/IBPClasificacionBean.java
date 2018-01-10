/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.sri.IBPClasificacion;
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
/*  26:    */ public class IBPClasificacionBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<IBPClasificacion> servicioIBPClasificacion;
/*  32:    */   private IBPClasificacion ibpClasificacion;
/*  33:    */   private LazyDataModel<IBPClasificacion> listaIBPClasificacion;
/*  34:    */   private DataTable dataTableIBPClasificacion;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaIBPClasificacion = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<IBPClasificacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 75 */         List<IBPClasificacion> lista = new ArrayList();
/*  46: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         try
/*  48:    */         {
/*  49: 78 */           lista = IBPClasificacionBean.this.servicioIBPClasificacion.obtenerListaPorPagina(IBPClasificacion.class, startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         }
/*  51:    */         catch (Exception e)
/*  52:    */         {
/*  53: 81 */           e.printStackTrace();
/*  54:    */         }
/*  55: 83 */         IBPClasificacionBean.this.listaIBPClasificacion.setRowCount(IBPClasificacionBean.this.servicioIBPClasificacion.contarPorCriterio(IBPClasificacion.class, filters));
/*  56: 84 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63: 97 */     if ((getIbpClasificacion() != null) && (getIbpClasificacion().getIdIBPClasificacion() != 0)) {
/*  64: 98 */       setEditado(true);
/*  65:    */     } else {
/*  66:100 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:103 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:114 */       this.servicioIBPClasificacion.guardarValidar(this.ibpClasificacion);
/*  76:115 */       cargarDatos();
/*  77:    */       
/*  78:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:    */     }
/*  80:    */     catch (AS2Exception e)
/*  81:    */     {
/*  82:119 */       JsfUtil.addErrorMessage(e, "");
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89:124 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:135 */     if ((getIbpClasificacion() != null) && (getIbpClasificacion().getIdIBPClasificacion() != 0)) {
/*  95:    */       try
/*  96:    */       {
/*  97:137 */         this.servicioIBPClasificacion.eliminar(this.ibpClasificacion);
/*  98:138 */         cargarDatos();
/*  99:    */         
/* 100:140 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */       }
/* 102:    */       catch (Exception e)
/* 103:    */       {
/* 104:142 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:143 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */       }
/* 107:    */     } else {
/* 108:146 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 109:    */     }
/* 110:149 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:160 */     setEditado(false);
/* 116:    */     try
/* 117:    */     {
/* 118:163 */       limpiar();
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:166 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 123:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:169 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String limpiar()
/* 129:    */   {
/* 130:179 */     this.ibpClasificacion = new IBPClasificacion();
/* 131:180 */     this.ibpClasificacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 132:181 */     this.ibpClasificacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 133:182 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public IBPClasificacion getIbpClasificacion()
/* 137:    */   {
/* 138:186 */     return this.ibpClasificacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIbpClasificacion(IBPClasificacion ibpClasificacion)
/* 142:    */   {
/* 143:190 */     this.ibpClasificacion = ibpClasificacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DataTable getDataTableIBPClasificacion()
/* 147:    */   {
/* 148:199 */     return this.dataTableIBPClasificacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDataTableIBPClasificacion(DataTable dataTableIBPClasificacion)
/* 152:    */   {
/* 153:203 */     this.dataTableIBPClasificacion = dataTableIBPClasificacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public LazyDataModel<IBPClasificacion> getListaIBPClasificacion()
/* 157:    */   {
/* 158:207 */     if (this.listaIBPClasificacion == null) {
/* 159:208 */       cargarDatos();
/* 160:    */     }
/* 161:210 */     return this.listaIBPClasificacion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setListaIBPClasificacion(LazyDataModel<IBPClasificacion> listaIBPClasificacion)
/* 165:    */   {
/* 166:214 */     this.listaIBPClasificacion = listaIBPClasificacion;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.IBPClasificacionBean
 * JD-Core Version:    0.7.0.1
 */