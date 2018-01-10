/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.DetallePagoComision;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PagoComision;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.JsfUtil;
/*  19:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPagoComision;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Calendar;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class PagoComisionBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   private ServicioPagoComision servicioPagoComision;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumento servicioDocumento;
/*  45:    */   private PagoComision pagoComision;
/*  46:    */   private LazyDataModel<PagoComision> listaPagoComision;
/*  47:    */   private List<Documento> listaDocumento;
/*  48:    */   private DataTable dtPagoComision;
/*  49:    */   private DataTable dtDetallePagoComision;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 75 */     this.listaPagoComision = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  57:    */       
/*  58:    */       public List<PagoComision> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 82 */         List<PagoComision> lista = new ArrayList();
/*  61: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63: 85 */         lista = PagoComisionBean.this.servicioPagoComision.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  64:    */         
/*  65: 87 */         PagoComisionBean.this.listaPagoComision.setRowCount(PagoComisionBean.this.servicioPagoComision.contarPorCriterio(filters));
/*  66: 88 */         return lista;
/*  67:    */       }
/*  68:    */     };
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73:100 */     if ((getPagoComision() != null) && (getPagoComision().getId() != 0))
/*  74:    */     {
/*  75:101 */       this.pagoComision = this.servicioPagoComision.cargarDetalle(this.pagoComision.getId());
/*  76:102 */       setEditado(true);
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82:106 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:117 */       this.servicioPagoComision.guardar(this.pagoComision);
/*  90:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  91:119 */       limpiar();
/*  92:    */     }
/*  93:    */     catch (AS2Exception e)
/*  94:    */     {
/*  95:121 */       JsfUtil.addErrorMessage(e, "");
/*  96:    */     }
/*  97:    */     catch (ExcepcionAS2 e)
/*  98:    */     {
/*  99:123 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:125 */       e.printStackTrace();
/* 104:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 105:    */     }
/* 106:128 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:138 */     if ((getPagoComision() != null) && (getPagoComision().getId() != 0)) {
/* 112:    */       try
/* 113:    */       {
/* 114:140 */         this.servicioPagoComision.eliminar(this.pagoComision);
/* 115:141 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 116:142 */         limpiar();
/* 117:    */       }
/* 118:    */       catch (Exception e)
/* 119:    */       {
/* 120:144 */         e.printStackTrace();
/* 121:145 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 122:146 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 123:    */       }
/* 124:    */     } else {
/* 125:149 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 126:    */     }
/* 127:151 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void crearPagoComision()
/* 131:    */   {
/* 132:155 */     this.pagoComision = new PagoComision();
/* 133:156 */     this.pagoComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 134:157 */     this.pagoComision.setIdSucursal(AppUtil.getSucursal().getId());
/* 135:158 */     this.pagoComision.setEstado(Estado.ELABORADO);
/* 136:    */     
/* 137:    */ 
/* 138:161 */     PagoComision ultimoPagoComision = this.servicioPagoComision.obtenerUltimoPagoComision();
/* 139:162 */     Calendar hoy = Calendar.getInstance();
/* 140:163 */     Mes mesSugerido = Mes.values()[hoy.get(2)];
/* 141:164 */     int anioSugerido = hoy.get(1);
/* 142:165 */     if (ultimoPagoComision != null)
/* 143:    */     {
/* 144:167 */       Date ultimaFecha = FuncionesUtiles.getFechaFinMes(ultimoPagoComision.getAnioFinal(), ultimoPagoComision.getMesFinal().getNumero());
/* 145:168 */       Calendar proximaFecha = Calendar.getInstance();
/* 146:169 */       proximaFecha.setTime(ultimaFecha);
/* 147:170 */       proximaFecha.add(5, 1);
/* 148:    */       
/* 149:172 */       mesSugerido = Mes.values()[proximaFecha.get(2)];
/* 150:173 */       anioSugerido = proximaFecha.get(1);
/* 151:    */     }
/* 152:175 */     this.pagoComision.setMesInicial(mesSugerido);
/* 153:176 */     this.pagoComision.setMesFinal(mesSugerido);
/* 154:177 */     this.pagoComision.setAnioInicial(anioSugerido);
/* 155:178 */     this.pagoComision.setAnioFinal(anioSugerido);
/* 156:179 */     this.pagoComision.setFecha(hoy.getTime());
/* 157:181 */     if (getListaDocumento().size() > 0) {
/* 158:182 */       this.pagoComision.setDocumento((Documento)getListaDocumento().get(0));
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String limpiar()
/* 163:    */   {
/* 164:188 */     crearPagoComision();
/* 165:189 */     setEditado(false);
/* 166:190 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String cargarDatos()
/* 170:    */   {
/* 171:195 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void generarComisiones()
/* 175:    */   {
/* 176:199 */     this.servicioPagoComision.generarPagosComision(this.pagoComision);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Integer getClasificacionPagoComisiones()
/* 180:    */   {
/* 181:203 */     return ParametrosSistema.getClasificadorPagoComisiones(AppUtil.getOrganizacion().getId());
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Mes> getListaMes()
/* 185:    */   {
/* 186:207 */     List<Mes> lista = new ArrayList();
/* 187:208 */     for (Mes mes : Mes.values()) {
/* 188:209 */       lista.add(mes);
/* 189:    */     }
/* 190:211 */     return lista;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<Documento> getListaDocumento()
/* 194:    */   {
/* 195:215 */     if (this.listaDocumento == null) {
/* 196:    */       try
/* 197:    */       {
/* 198:217 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_COMISION, AppUtil.getOrganizacion().getId());
/* 199:    */       }
/* 200:    */       catch (ExcepcionAS2 e)
/* 201:    */       {
/* 202:219 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 203:    */       }
/* 204:    */     }
/* 205:222 */     return this.listaDocumento;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<DetallePagoComision> getListaDetallePagoComision()
/* 209:    */   {
/* 210:226 */     List<DetallePagoComision> lista = new ArrayList();
/* 211:227 */     for (DetallePagoComision detallePagoComision : this.pagoComision.getListaDetallePagoComision()) {
/* 212:228 */       if (!detallePagoComision.isEliminado()) {
/* 213:229 */         lista.add(detallePagoComision);
/* 214:    */       }
/* 215:    */     }
/* 216:232 */     return lista;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public PagoComision getPagoComision()
/* 220:    */   {
/* 221:236 */     return this.pagoComision;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setPagoComision(PagoComision pagoComision)
/* 225:    */   {
/* 226:240 */     this.pagoComision = pagoComision;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public LazyDataModel<PagoComision> getListaPagoComision()
/* 230:    */   {
/* 231:244 */     return this.listaPagoComision;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setListaPagoComision(LazyDataModel<PagoComision> listaPagoComision)
/* 235:    */   {
/* 236:248 */     this.listaPagoComision = listaPagoComision;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public DataTable getDtPagoComision()
/* 240:    */   {
/* 241:252 */     return this.dtPagoComision;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setDtPagoComision(DataTable dtPagoComision)
/* 245:    */   {
/* 246:256 */     this.dtPagoComision = dtPagoComision;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public DataTable getDtDetallePagoComision()
/* 250:    */   {
/* 251:260 */     return this.dtDetallePagoComision;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setDtDetallePagoComision(DataTable dtDetallePagoComision)
/* 255:    */   {
/* 256:264 */     this.dtDetallePagoComision = dtDetallePagoComision;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.PagoComisionBean
 * JD-Core Version:    0.7.0.1
 */