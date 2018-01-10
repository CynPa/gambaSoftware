/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.sri.IBPClasificacion;
/*   8:    */ import com.asinfo.as2.entities.sri.IBPMarca;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.JsfUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class IBPMarcaBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<IBPMarca> servicioIBPMarca;
/*  34:    */   @EJB
/*  35:    */   private ServicioGenerico<IBPClasificacion> servicioIBPClasificacion;
/*  36:    */   private IBPMarca ibpMarca;
/*  37:    */   private LazyDataModel<IBPMarca> listaIBPMarca;
/*  38:    */   private DataTable dataTableIBPMarca;
/*  39:    */   private List<IBPClasificacion> listaIBPClasificacion;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 75 */     this.listaIBPMarca = new LazyDataModel()
/*  45:    */     {
/*  46:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  47:    */       
/*  48:    */       public List<IBPMarca> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 81 */         List<IBPMarca> lista = new ArrayList();
/*  51: 82 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52: 83 */         List<String> listaCampos = new ArrayList();
/*  53: 84 */         listaCampos.add("ibpClasificacion");
/*  54:    */         try
/*  55:    */         {
/*  56: 86 */           lista = IBPMarcaBean.this.servicioIBPMarca.obtenerListaPorPagina(IBPMarca.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/*  57:    */         }
/*  58:    */         catch (Exception e)
/*  59:    */         {
/*  60: 89 */           e.printStackTrace();
/*  61:    */         }
/*  62: 91 */         IBPMarcaBean.this.listaIBPMarca.setRowCount(IBPMarcaBean.this.servicioIBPMarca.contarPorCriterio(IBPMarca.class, filters));
/*  63: 92 */         return lista;
/*  64:    */       }
/*  65:    */     };
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:105 */     if ((getIbpMarca() != null) && (getIbpMarca().getIdIBPMarca() != 0))
/*  71:    */     {
/*  72:106 */       List<String> listaCampos = new ArrayList();
/*  73:107 */       listaCampos.add("ibpClasificacion");
/*  74:108 */       this.ibpMarca = ((IBPMarca)this.servicioIBPMarca.cargarDetalle(IBPMarca.class, this.ibpMarca.getId(), listaCampos));
/*  75:109 */       setEditado(true);
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  80:    */     }
/*  81:114 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String guardar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:125 */       this.servicioIBPMarca.guardarValidar(this.ibpMarca);
/*  89:126 */       cargarDatos();
/*  90:    */       
/*  91:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:    */     }
/*  93:    */     catch (AS2Exception e)
/*  94:    */     {
/*  95:130 */       JsfUtil.addErrorMessage(e, "");
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 100:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:135 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String eliminar()
/* 106:    */   {
/* 107:146 */     if ((getIbpMarca() != null) && (getIbpMarca().getIdIBPMarca() != 0)) {
/* 108:    */       try
/* 109:    */       {
/* 110:148 */         this.servicioIBPMarca.eliminar(this.ibpMarca);
/* 111:149 */         cargarDatos();
/* 112:    */         
/* 113:151 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */       }
/* 115:    */       catch (Exception e)
/* 116:    */       {
/* 117:153 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:154 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */       }
/* 120:    */     } else {
/* 121:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 122:    */     }
/* 123:160 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:171 */     setEditado(false);
/* 129:    */     try
/* 130:    */     {
/* 131:174 */       limpiar();
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:177 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 136:178 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 137:    */     }
/* 138:180 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String limpiar()
/* 142:    */   {
/* 143:190 */     this.ibpMarca = new IBPMarca();
/* 144:191 */     this.ibpMarca.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 145:192 */     this.ibpMarca.setIdSucursal(AppUtil.getSucursal().getId());
/* 146:193 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public IBPMarca getIbpMarca()
/* 150:    */   {
/* 151:203 */     return this.ibpMarca;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIbpMarca(IBPMarca ibpMarca)
/* 155:    */   {
/* 156:213 */     this.ibpMarca = ibpMarca;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DataTable getDataTableIBPMarca()
/* 160:    */   {
/* 161:222 */     return this.dataTableIBPMarca;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDataTableIBPMarca(DataTable dataTableIBPMarca)
/* 165:    */   {
/* 166:226 */     this.dataTableIBPMarca = dataTableIBPMarca;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public LazyDataModel<IBPMarca> getListaIBPMarca()
/* 170:    */   {
/* 171:230 */     if (this.listaIBPMarca == null) {
/* 172:231 */       cargarDatos();
/* 173:    */     }
/* 174:233 */     return this.listaIBPMarca;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setListaIBPMarca(LazyDataModel<IBPMarca> listaIBPMarca)
/* 178:    */   {
/* 179:237 */     this.listaIBPMarca = listaIBPMarca;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<IBPClasificacion> getListaIBPClasificacion()
/* 183:    */   {
/* 184:241 */     if (this.listaIBPClasificacion == null)
/* 185:    */     {
/* 186:242 */       Map<String, String> filters = new HashMap();
/* 187:243 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 188:244 */       filters.put("activo", "true");
/* 189:245 */       this.listaIBPClasificacion = this.servicioIBPClasificacion.obtenerListaCombo(IBPClasificacion.class, "nombre", true, filters);
/* 190:    */     }
/* 191:247 */     return this.listaIBPClasificacion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaIBPClasificacion(List<IBPClasificacion> listaIBPClasificacion)
/* 195:    */   {
/* 196:251 */     this.listaIBPClasificacion = listaIBPClasificacion;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.IBPMarcaBean
 * JD-Core Version:    0.7.0.1
 */