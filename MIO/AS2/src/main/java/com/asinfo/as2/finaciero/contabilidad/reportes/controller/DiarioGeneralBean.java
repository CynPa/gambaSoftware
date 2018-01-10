/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioDiarioGeneral;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ import org.primefaces.component.datatable.DataTable;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class DiarioGeneralBean
/*  32:    */   extends PageController
/*  33:    */ {
/*  34:    */   @EJB
/*  35:    */   private ServicioAsiento servicioAsiento;
/*  36:    */   private Asiento asiento;
/*  37:    */   private static final long serialVersionUID = 1308228707838638922L;
/*  38:    */   @EJB
/*  39:    */   private ServicioDiarioGeneral servicioDiarioGeneral;
/*  40:    */   @EJB
/*  41:    */   private ServicioSucursal servicioSucursal;
/*  42:    */   @EJB
/*  43:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  44:    */   private Date fechaDesde;
/*  45:    */   private Date fechaHasta;
/*  46:    */   @Min(1900L)
/*  47: 75 */   private int anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  48: 76 */   private int mesDesde = 1;
/*  49:    */   @Min(1900L)
/*  50: 79 */   private int anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  51: 80 */   private int mesHasta = FuncionesUtiles.obtenerMesActual();
/*  52:    */   private Sucursal sucursal;
/*  53:    */   private TipoAsiento tipoAsiento;
/*  54:    */   private List<Sucursal> listaSucursal;
/*  55:    */   private List<TipoAsiento> listaTipoAsiento;
/*  56:    */   private List<SelectItem> listaMes;
/*  57:    */   private List<Asiento> listaAsiento;
/*  58: 92 */   private List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  59:    */   private DataTable dtDetalleDiarioGeneral;
/*  60:    */   
/*  61:    */   public String guardar()
/*  62:    */   {
/*  63:100 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String procesar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:105 */       this.fechaDesde = FuncionesUtiles.getFecha(1, this.mesDesde, this.anioDesde);
/*  71:106 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta);
/*  72:107 */       this.listaAsiento = this.servicioDiarioGeneral.cargarDatos(this.fechaDesde, this.fechaHasta, getSucursal().getIdSucursal(), getTipoAsiento().getId(), this.asiento);
/*  73:    */     }
/*  74:    */     catch (ExcepcionAS2Financiero e)
/*  75:    */     {
/*  76:109 */       limpiar();
/*  77:110 */       this.listaAsiento = new ArrayList();
/*  78:111 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  79:112 */       LOG.error("ERROR AL PROCESAR", e);
/*  80:    */     }
/*  81:114 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String limpiar()
/*  85:    */   {
/*  86:118 */     this.listaAsiento = new ArrayList();
/*  87:119 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<SelectItem> getListaMes()
/*  91:    */   {
/*  92:123 */     if (this.listaMes == null)
/*  93:    */     {
/*  94:124 */       this.listaMes = new ArrayList();
/*  95:125 */       for (Mes t : Mes.values())
/*  96:    */       {
/*  97:126 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/*  98:127 */         this.listaMes.add(item);
/*  99:    */       }
/* 100:    */     }
/* 101:130 */     return this.listaMes;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<Asiento> autocompletarAsiento(String consulta)
/* 105:    */   {
/* 106:134 */     HashMap<String, String> filters = new HashMap();
/* 107:135 */     if ((null != this.tipoAsiento) && (this.tipoAsiento.getId() != 0)) {
/* 108:136 */       filters.put("tipoAsiento.idTipoAsiento", String.valueOf(this.tipoAsiento.getId()));
/* 109:    */     }
/* 110:138 */     filters.put("estado", "!=" + Estado.ANULADO);
/* 111:139 */     filters.put("numero", "%" + consulta.trim() + "%");
/* 112:    */     
/* 113:141 */     return this.servicioAsiento.obtenerListaCombo("numero", true, filters);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setListaMes(List<SelectItem> listaMes)
/* 117:    */   {
/* 118:145 */     this.listaMes = listaMes;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Date getFechaDesde()
/* 122:    */   {
/* 123:149 */     return this.fechaDesde;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setFechaDesde(Date fechaDesde)
/* 127:    */   {
/* 128:153 */     this.fechaDesde = fechaDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Date getFechaHasta()
/* 132:    */   {
/* 133:157 */     return this.fechaHasta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setFechaHasta(Date fechaHasta)
/* 137:    */   {
/* 138:161 */     this.fechaHasta = fechaHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getMesDesde()
/* 142:    */   {
/* 143:165 */     return this.mesDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setMesDesde(int mesDesde)
/* 147:    */   {
/* 148:169 */     this.mesDesde = mesDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getAnioDesde()
/* 152:    */   {
/* 153:173 */     return this.anioDesde;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setAnioDesde(int anioDesde)
/* 157:    */   {
/* 158:177 */     this.anioDesde = anioDesde;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getMesHasta()
/* 162:    */   {
/* 163:181 */     return this.mesHasta;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setMesHasta(int mesHasta)
/* 167:    */   {
/* 168:185 */     this.mesHasta = mesHasta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getAnioHasta()
/* 172:    */   {
/* 173:189 */     return this.anioHasta;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setAnioHasta(int anioHasta)
/* 177:    */   {
/* 178:193 */     this.anioHasta = anioHasta;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 182:    */   {
/* 183:197 */     return this.listaDetalleAsiento;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 187:    */   {
/* 188:201 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public DataTable getDtDetalleDiarioGeneral()
/* 192:    */   {
/* 193:205 */     return this.dtDetalleDiarioGeneral;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setDtDetalleDiarioGeneral(DataTable dtDetalleDiarioGeneral)
/* 197:    */   {
/* 198:209 */     this.dtDetalleDiarioGeneral = dtDetalleDiarioGeneral;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<Asiento> getListaAsiento()
/* 202:    */   {
/* 203:213 */     return this.listaAsiento;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaAsiento(List<Asiento> listaAsiento)
/* 207:    */   {
/* 208:217 */     this.listaAsiento = listaAsiento;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Sucursal getSucursal()
/* 212:    */   {
/* 213:226 */     if (this.sucursal == null) {
/* 214:227 */       this.sucursal = new Sucursal();
/* 215:    */     }
/* 216:229 */     return this.sucursal;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setSucursal(Sucursal sucursal)
/* 220:    */   {
/* 221:239 */     this.sucursal = sucursal;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public TipoAsiento getTipoAsiento()
/* 225:    */   {
/* 226:248 */     if (this.tipoAsiento == null) {
/* 227:249 */       this.tipoAsiento = new TipoAsiento();
/* 228:    */     }
/* 229:251 */     return this.tipoAsiento;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setTipoAsiento(TipoAsiento tipoAsiento)
/* 233:    */   {
/* 234:261 */     this.tipoAsiento = tipoAsiento;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<Sucursal> getListaSucursal()
/* 238:    */   {
/* 239:270 */     if (this.listaSucursal == null) {
/* 240:271 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 241:    */     }
/* 242:273 */     return this.listaSucursal;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 246:    */   {
/* 247:283 */     this.listaSucursal = listaSucursal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<TipoAsiento> getListaTipoAsiento()
/* 251:    */   {
/* 252:292 */     if (this.listaTipoAsiento == null) {
/* 253:293 */       this.listaTipoAsiento = this.servicioTipoAsiento.obtenerListaCombo("nombre", true, null);
/* 254:    */     }
/* 255:295 */     return this.listaTipoAsiento;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setListaTipoAsiento(List<TipoAsiento> listaTipoAsiento)
/* 259:    */   {
/* 260:305 */     this.listaTipoAsiento = listaTipoAsiento;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Asiento getAsiento()
/* 264:    */   {
/* 265:309 */     return this.asiento;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setAsiento(Asiento asiento)
/* 269:    */   {
/* 270:313 */     this.asiento = asiento;
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.DiarioGeneralBean
 * JD-Core Version:    0.7.0.1
 */