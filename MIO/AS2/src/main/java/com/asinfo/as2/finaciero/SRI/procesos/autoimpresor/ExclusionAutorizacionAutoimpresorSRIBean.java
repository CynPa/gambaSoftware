/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos.autoimpresor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   7:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoAutoimpresorSRI;
/*   9:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  10:    */ import com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ExclusionAutorizacionAutoimpresorSRIBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  32:    */   @EJB
/*  33:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  34:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  35:    */   private AutorizacionDocumentoAutoimpresorSRI autorizacionDocumentoAutoimpresorSRI;
/*  36:    */   private AutorizacionPuntoDeVentaAutoimpresorSRI autorizacionPuntoDeVentaAutoimpresorSRI;
/*  37:    */   private Date fecha;
/*  38:    */   private DataTable dtDocumentoBaseAsignados;
/*  39:    */   private DataTable dtAutorizacionDocumentoAutoimpresorSRI;
/*  40:    */   private DataTable dtPuntoDeVentaAsignados;
/*  41:    */   private DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/*  42:    */   private DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 88 */       setFecha(Calendar.getInstance().getTime());
/*  50: 89 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion()
/*  51: 90 */         .getIdOrganizacion());
/*  52: 91 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/*  53: 92 */       setEditado(true);
/*  54:    */     }
/*  55:    */     catch (ExcepcionAS2Financiero e)
/*  56:    */     {
/*  57: 94 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearEntidad() {}
/*  62:    */   
/*  63:    */   public void excluirDocumentoBase()
/*  64:    */   {
/*  65:110 */     this.autorizacionDocumentoAutoimpresorSRI.setActivo(false);
/*  66:111 */     excluirAutorizacionDocumentoPuntoDeVentaSRI(this.autorizacionDocumentoAutoimpresorSRI.getDocumentoBase(), null);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void excluirPuntoDeVenta()
/*  70:    */   {
/*  71:115 */     this.autorizacionPuntoDeVentaAutoimpresorSRI.setActivo(false);
/*  72:116 */     excluirAutorizacionDocumentoPuntoDeVentaSRI(null, this.autorizacionPuntoDeVentaAutoimpresorSRI.getPuntoDeVenta());
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void excluirAutorizacionDocumentoPuntoDeVentaSRI(DocumentoBase documentoBase, PuntoDeVenta puntoDeVenta)
/*  76:    */   {
/*  77:120 */     if (documentoBase != null) {
/*  78:121 */       for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : this.autorizacionAutoimpresorSRI
/*  79:122 */         .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/*  80:123 */         if (adpv.getDocumentoBase() == documentoBase)
/*  81:    */         {
/*  82:124 */           adpv.setFechaExclusion(this.fecha);
/*  83:125 */           adpv.setActivo(false);
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:129 */     if (puntoDeVenta != null) {
/*  88:131 */       for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpv : this.autorizacionAutoimpresorSRI
/*  89:132 */         .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()) {
/*  90:133 */         if (adpv.getPuntoDeVenta().getId() == puntoDeVenta.getId())
/*  91:    */         {
/*  92:134 */           adpv.setFechaExclusion(this.fecha);
/*  93:135 */           adpv.setActivo(false);
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String editar()
/* 100:    */   {
/* 101:147 */     if (getAutorizacionAutoimpresorSRI().getIdAutorizacionAutoimpresorSRI() > 0)
/* 102:    */     {
/* 103:148 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/* 104:149 */       setEditado(true);
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:151 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 109:    */     }
/* 110:153 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String guardar()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:163 */       this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.EXCLUSION, this.fecha);
/* 118:164 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 119:165 */       setEditado(false);
/* 120:166 */       limpiar();
/* 121:    */     }
/* 122:    */     catch (ExcepcionAS2 e)
/* 123:    */     {
/* 124:168 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 125:169 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:171 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 130:172 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 131:    */     }
/* 132:174 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String eliminar()
/* 136:    */   {
/* 137:    */     try
/* 138:    */     {
/* 139:184 */       this.servicioAutorizacionAutoimpresorSRI.eliminar(this.autorizacionAutoimpresorSRI);
/* 140:185 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 145:188 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 146:    */     }
/* 147:190 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String cargarDatos()
/* 151:    */   {
/* 152:199 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String limpiar()
/* 156:    */   {
/* 157:208 */     crearEntidad();
/* 158:209 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 162:    */   {
/* 163:226 */     if (this.autorizacionAutoimpresorSRI == null) {
/* 164:227 */       crearEntidad();
/* 165:    */     }
/* 166:229 */     return this.autorizacionAutoimpresorSRI;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 170:    */   {
/* 171:239 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public DataTable getDtAutorizacionDocumentoAutoimpresorSRI()
/* 175:    */   {
/* 176:243 */     return this.dtAutorizacionDocumentoAutoimpresorSRI;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDtAutorizacionDocumentoAutoimpresorSRI(DataTable dtAutorizacionDocumentoAutoimpresorSRI)
/* 180:    */   {
/* 181:247 */     this.dtAutorizacionDocumentoAutoimpresorSRI = dtAutorizacionDocumentoAutoimpresorSRI;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public AutorizacionDocumentoAutoimpresorSRI getAutorizacionDocumentoAutoimpresorSRI()
/* 185:    */   {
/* 186:251 */     return this.autorizacionDocumentoAutoimpresorSRI;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setAutorizacionDocumentoAutoimpresorSRI(AutorizacionDocumentoAutoimpresorSRI autorizacionDocumentoAutoimpresorSRI)
/* 190:    */   {
/* 191:255 */     this.autorizacionDocumentoAutoimpresorSRI = autorizacionDocumentoAutoimpresorSRI;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public AutorizacionPuntoDeVentaAutoimpresorSRI getAutorizacionPuntoDeVentaAutoimpresorSRI()
/* 195:    */   {
/* 196:259 */     return this.autorizacionPuntoDeVentaAutoimpresorSRI;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setAutorizacionPuntoDeVentaAutoimpresorSRI(AutorizacionPuntoDeVentaAutoimpresorSRI autorizacionPuntoDeVentaAutoimpresorSRI)
/* 200:    */   {
/* 201:263 */     this.autorizacionPuntoDeVentaAutoimpresorSRI = autorizacionPuntoDeVentaAutoimpresorSRI;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public DataTable getDtDocumentoBaseAsignados()
/* 205:    */   {
/* 206:267 */     return this.dtDocumentoBaseAsignados;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setDtDocumentoBaseAsignados(DataTable dtDocumentoBaseAsignados)
/* 210:    */   {
/* 211:271 */     this.dtDocumentoBaseAsignados = dtDocumentoBaseAsignados;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DataTable getDtPuntoDeVentaAsignados()
/* 215:    */   {
/* 216:275 */     return this.dtPuntoDeVentaAsignados;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDtPuntoDeVentaAsignados(DataTable dtPuntoDeVentaAsignados)
/* 220:    */   {
/* 221:279 */     this.dtPuntoDeVentaAsignados = dtPuntoDeVentaAsignados;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public DataTable getDtAutorizacionPuntoDeVentaAutoimpresorSRI()
/* 225:    */   {
/* 226:283 */     return this.dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setDtAutorizacionPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionPuntoDeVentaAutoimpresorSRI)
/* 230:    */   {
/* 231:287 */     this.dtAutorizacionPuntoDeVentaAutoimpresorSRI = dtAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public DataTable getDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 235:    */   {
/* 236:291 */     return this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setDtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(DataTable dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 240:    */   {
/* 241:295 */     this.dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = dtAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Date getFecha()
/* 245:    */   {
/* 246:299 */     return this.fecha;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setFecha(Date fecha)
/* 250:    */   {
/* 251:303 */     this.fecha = fecha;
/* 252:    */   }
/* 253:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.ExclusionAutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */