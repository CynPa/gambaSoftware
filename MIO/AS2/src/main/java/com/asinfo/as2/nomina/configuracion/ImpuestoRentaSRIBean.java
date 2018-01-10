/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.ImpuestoRentaSRI;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioImpuestoRentaSRI;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import java.io.BufferedInputStream;
/*  15:    */ import java.io.InputStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.event.FileUploadEvent;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ import org.primefaces.model.UploadedFile;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ImpuestoRentaSRIBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioImpuestoRentaSRI servicioImpuestoRentaSRI;
/*  39:    */   private ImpuestoRentaSRI impuestoRentaSRI;
/*  40: 62 */   private int anio = FuncionesUtiles.obtenerAnioActual();
/*  41:    */   private LazyDataModel<ImpuestoRentaSRI> listaImpuestoRentaSRI;
/*  42:    */   private DataTable dtImpuestoRentaSRI;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 80 */     this.listaImpuestoRentaSRI = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<ImpuestoRentaSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 87 */         List<ImpuestoRentaSRI> lista = new ArrayList();
/*  54: 88 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55: 89 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  56: 90 */         filters.put("anio", "" + ImpuestoRentaSRIBean.this.getAnio());
/*  57: 91 */         lista = ImpuestoRentaSRIBean.this.servicioImpuestoRentaSRI.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  58: 92 */         ImpuestoRentaSRIBean.this.listaImpuestoRentaSRI.setRowCount(ImpuestoRentaSRIBean.this.servicioImpuestoRentaSRI.contarPorCriterio(filters));
/*  59: 93 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void crearImpuestoRentaSRI()
/*  65:    */   {
/*  66:106 */     this.impuestoRentaSRI = new ImpuestoRentaSRI();
/*  67:107 */     this.impuestoRentaSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  68:108 */     this.impuestoRentaSRI.setIdSucursal(AppUtil.getSucursal().getId());
/*  69:109 */     this.impuestoRentaSRI.setAnio(new Date().getYear() + 1900);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String editar()
/*  73:    */   {
/*  74:119 */     if ((getImpuestoRentaSRI() != null) && (getImpuestoRentaSRI().getIdImpuestoRentaSRI() != 0)) {
/*  75:120 */       setEditado(true);
/*  76:    */     } else {
/*  77:122 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  78:    */     }
/*  79:124 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String guardar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:134 */       if (isEditado()) {
/*  87:135 */         this.impuestoRentaSRI.setEditado(true);
/*  88:    */       } else {
/*  89:137 */         this.impuestoRentaSRI.setEditado(false);
/*  90:    */       }
/*  91:139 */       this.servicioImpuestoRentaSRI.guardar(this.impuestoRentaSRI);
/*  92:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  93:141 */       setEditado(false);
/*  94:142 */       limpiar();
/*  95:    */     }
/*  96:    */     catch (ExcepcionAS2 e)
/*  97:    */     {
/*  98:144 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  99:145 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 100:    */     }
/* 101:    */     catch (AS2Exception e)
/* 102:    */     {
/* 103:147 */       JsfUtil.addErrorMessage(e, "");
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 108:150 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 109:    */     }
/* 110:152 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String eliminar()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:162 */       this.servicioImpuestoRentaSRI.eliminar(this.impuestoRentaSRI);
/* 118:163 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 123:166 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 124:    */     }
/* 125:168 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String cargarImpuestoRentaSRI(FileUploadEvent event)
/* 129:    */   {
/* 130:173 */     List<ImpuestoRentaSRI> listaImpuestoRentaSRI = this.servicioImpuestoRentaSRI.obtenerTablaPorAnio(FuncionesUtiles.obtenerAnioActual(), 
/* 131:174 */       AppUtil.getOrganizacion().getIdOrganizacion());
/* 132:175 */     if (!listaImpuestoRentaSRI.isEmpty()) {
/* 133:176 */       addErrorMessage(getLanguageController().getMensaje("msg_valores_impuesto_renta_configurados"));
/* 134:    */     } else {
/* 135:    */       try
/* 136:    */       {
/* 137:180 */         InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 138:181 */         this.servicioImpuestoRentaSRI.cargarImpuestoRentaSRI(AppUtil.getOrganizacion().getId(), input, 4);
/* 139:182 */         addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 140:    */       }
/* 141:    */       catch (ExcepcionAS2 e)
/* 142:    */       {
/* 143:184 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 144:185 */         LOG.error("ERROR AL MIGRAR ", e);
/* 145:186 */         e.printStackTrace();
/* 146:    */       }
/* 147:    */       catch (Exception e)
/* 148:    */       {
/* 149:188 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 150:189 */         e.printStackTrace();
/* 151:    */       }
/* 152:    */     }
/* 153:192 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String cargarDatos()
/* 157:    */   {
/* 158:201 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String limpiar()
/* 162:    */   {
/* 163:210 */     crearImpuestoRentaSRI();
/* 164:211 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ImpuestoRentaSRI getImpuestoRentaSRI()
/* 168:    */   {
/* 169:227 */     if (this.impuestoRentaSRI == null) {
/* 170:228 */       this.impuestoRentaSRI = new ImpuestoRentaSRI();
/* 171:    */     }
/* 172:230 */     return this.impuestoRentaSRI;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setImpuestoRentaSRI(ImpuestoRentaSRI impuestoRentaSRI)
/* 176:    */   {
/* 177:240 */     this.impuestoRentaSRI = impuestoRentaSRI;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public LazyDataModel<ImpuestoRentaSRI> getListaImpuestoRentaSRI()
/* 181:    */   {
/* 182:249 */     return this.listaImpuestoRentaSRI;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setListaImpuestoRentaSRI(LazyDataModel<ImpuestoRentaSRI> listaImpuestoRentaSRI)
/* 186:    */   {
/* 187:259 */     this.listaImpuestoRentaSRI = listaImpuestoRentaSRI;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public DataTable getDtImpuestoRentaSRI()
/* 191:    */   {
/* 192:268 */     return this.dtImpuestoRentaSRI;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setDtImpuestoRentaSRI(DataTable dtImpuestoRentaSRI)
/* 196:    */   {
/* 197:278 */     this.dtImpuestoRentaSRI = dtImpuestoRentaSRI;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public int getAnio()
/* 201:    */   {
/* 202:282 */     return this.anio;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setAnio(int anio)
/* 206:    */   {
/* 207:286 */     this.anio = anio;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getRutaPlantilla()
/* 211:    */   {
/* 212:291 */     return "/resources/plantillas/nomina/AS2 Impuesto Renta SRI.xls";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getNombrePlantilla()
/* 216:    */   {
/* 217:296 */     return "AS2 Impuesto Renta SRI.xls";
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.ImpuestoRentaSRIBean
 * JD-Core Version:    0.7.0.1
 */