/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.sri.IBPUnidad;
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
/*  26:    */ public class IBPUnidadBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<IBPUnidad> servicioIBPUnidad;
/*  32:    */   private IBPUnidad ibpUnidad;
/*  33:    */   private LazyDataModel<IBPUnidad> listaIBPUnidad;
/*  34:    */   private DataTable dataTableIBPUnidad;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaIBPUnidad = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<IBPUnidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 75 */         List<IBPUnidad> lista = new ArrayList();
/*  46: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         try
/*  48:    */         {
/*  49: 78 */           lista = IBPUnidadBean.this.servicioIBPUnidad.obtenerListaPorPagina(IBPUnidad.class, startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         }
/*  51:    */         catch (Exception e)
/*  52:    */         {
/*  53: 81 */           e.printStackTrace();
/*  54:    */         }
/*  55: 83 */         IBPUnidadBean.this.listaIBPUnidad.setRowCount(IBPUnidadBean.this.servicioIBPUnidad.contarPorCriterio(IBPUnidad.class, filters));
/*  56: 84 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63: 96 */     if ((getIbpUnidad() != null) && (getIbpUnidad().getIdIBPUnidad() != 0)) {
/*  64: 97 */       setEditado(true);
/*  65:    */     } else {
/*  66: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:102 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:113 */       this.servicioIBPUnidad.guardarValidar(this.ibpUnidad);
/*  76:114 */       cargarDatos();
/*  77:    */       
/*  78:116 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:    */     }
/*  80:    */     catch (AS2Exception e)
/*  81:    */     {
/*  82:118 */       JsfUtil.addErrorMessage(e, "");
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:121 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89:123 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:133 */     if ((getIbpUnidad() != null) && (getIbpUnidad().getIdIBPUnidad() != 0)) {
/*  95:    */       try
/*  96:    */       {
/*  97:135 */         this.servicioIBPUnidad.eliminar(this.ibpUnidad);
/*  98:136 */         cargarDatos();
/*  99:    */         
/* 100:138 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */       }
/* 102:    */       catch (Exception e)
/* 103:    */       {
/* 104:140 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:141 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */       }
/* 107:    */     } else {
/* 108:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 109:    */     }
/* 110:146 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String cargarDatos()
/* 114:    */   {
/* 115:157 */     setEditado(false);
/* 116:    */     try
/* 117:    */     {
/* 118:160 */       limpiar();
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:163 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 123:164 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:166 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String limpiar()
/* 129:    */   {
/* 130:176 */     this.ibpUnidad = new IBPUnidad();
/* 131:177 */     this.ibpUnidad.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 132:178 */     this.ibpUnidad.setIdSucursal(AppUtil.getSucursal().getId());
/* 133:179 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public IBPUnidad getIbpUnidad()
/* 137:    */   {
/* 138:188 */     return this.ibpUnidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIbpUnidad(IBPUnidad ibpUnidad)
/* 142:    */   {
/* 143:198 */     this.ibpUnidad = ibpUnidad;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DataTable getDataTableIBPUnidad()
/* 147:    */   {
/* 148:207 */     return this.dataTableIBPUnidad;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDataTableIBPUnidad(DataTable dataTableIBPUnidad)
/* 152:    */   {
/* 153:211 */     this.dataTableIBPUnidad = dataTableIBPUnidad;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public LazyDataModel<IBPUnidad> getListaIBPUnidad()
/* 157:    */   {
/* 158:215 */     if (this.listaIBPUnidad == null) {
/* 159:216 */       cargarDatos();
/* 160:    */     }
/* 161:218 */     return this.listaIBPUnidad;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setListaIBPUnidad(LazyDataModel<IBPUnidad> listaIBPUnidad)
/* 165:    */   {
/* 166:222 */     this.listaIBPUnidad = listaIBPUnidad;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.IBPUnidadBean
 * JD-Core Version:    0.7.0.1
 */