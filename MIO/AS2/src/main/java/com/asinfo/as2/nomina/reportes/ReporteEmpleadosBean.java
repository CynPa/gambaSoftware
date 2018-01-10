/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.enumeraciones.Genero;
/*  11:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteEmpleadosBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioDepartamento servicioDepartamento;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   private Departamento departamento;
/*  44:    */   private Sucursal sucursal;
/*  45:    */   private Genero genero;
/*  46:    */   
/*  47:    */   private static enum enumTipoReporte
/*  48:    */   {
/*  49: 42 */     ENTRADAS,  SALIDAS,  CUMPLEANOS;
/*  50:    */     
/*  51:    */     private enumTipoReporte() {}
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static enum enumEstado
/*  55:    */   {
/*  56: 46 */     ACTIVO,  INACTIVO,  TODOS;
/*  57:    */     
/*  58:    */     private enumEstado() {}
/*  59:    */   }
/*  60:    */   
/*  61: 60 */   private Date fechaDesde = FuncionesUtiles.sumarFechaAnios(new Date(), -10);
/*  62: 61 */   private Date fechaHasta = new Date();
/*  63: 62 */   private enumTipoReporte tipoReporte = enumTipoReporte.ENTRADAS;
/*  64: 63 */   private enumEstado estado = enumEstado.ACTIVO;
/*  65: 64 */   private int mes = FuncionesUtiles.obtenerMesActual();
/*  66:    */   private List<Departamento> listaDepartamento;
/*  67:    */   private List<Sucursal> listaSucursal;
/*  68:    */   private List<SelectItem> listaTipoReporte;
/*  69:    */   private List<SelectItem> listaEstado;
/*  70:    */   private List<SelectItem> listaMes;
/*  71:    */   
/*  72:    */   protected JRDataSource getJRDataSource()
/*  73:    */   {
/*  74: 75 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  75: 76 */     JRDataSource ds = null;
/*  76:    */     try
/*  77:    */     {
/*  78: 79 */       listaDatosReporte = this.servicioEmpresa.listaEmpledos(this.fechaDesde, this.fechaHasta, this.departamento, this.sucursal, this.genero, AppUtil.getOrganizacion()
/*  79: 80 */         .getIdOrganizacion(), this.estado.name(), this.tipoReporte.name(), this.mes);
/*  80:    */       
/*  81: 82 */       String[] fields = { "f_nombres", "f_apellidos", "f_estadoCivil", "f_genero", "f_fechaNacimiento", "f_fechaIngreso", "f_fechaSalida", "f_identificacion", "f_tipoSangre", "f_CodigoSectorial", "f_tipoContrato", "f_titulo", "f_departamento", "f_estado", "f_correo1", "f_correo2", "f_pagoCash", "f_gentilicio", "f_numeroCargasActivas", "f_telefono", "f_direccion", "f_barrio", "f_referencia", "f_ciudad", "f_cuenta", "f_banco", "f_tipoCuenta", "f_salario", "f_celular", "f_cargo", "f_sucursal", "f_tipoIdentificacion", "f_provincia", "f_ccNombre", "f_tiempoTrabajo" };
/*  82:    */       
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87: 88 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  88:    */     }
/*  89:    */     catch (Exception e)
/*  90:    */     {
/*  91: 90 */       e.printStackTrace();
/*  92:    */     }
/*  93: 92 */     return ds;
/*  94:    */   }
/*  95:    */   
/*  96:    */   @PostConstruct
/*  97:    */   public void init()
/*  98:    */   {
/*  99: 97 */     Calendar calfechaDesde = Calendar.getInstance();
/* 100: 98 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 101: 99 */     this.fechaDesde = calfechaDesde.getTime();
/* 102:100 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected String getCompileFileName()
/* 106:    */   {
/* 107:105 */     return "reporteEmpleados";
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected Map<String, Object> getReportParameters()
/* 111:    */   {
/* 112:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 113:112 */     reportParameters.put("p_departamento", getDepartamento() != null ? this.departamento.getNombre() : "Todos");
/* 114:113 */     reportParameters.put("p_sucursal", getSucursal() != null ? this.sucursal.getNombre() : "Todos");
/* 115:114 */     reportParameters.put("p_genero", getGenero() != null ? "Femenino" : this.genero.equals(Genero.MASCULINO) ? "Masculino" : "Todos");
/* 116:115 */     reportParameters.put("p_fechaDesde", getFechaDesde());
/* 117:116 */     reportParameters.put("p_fechaHasta", getFechaHasta());
/* 118:117 */     return reportParameters;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String execute()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:123 */       super.prepareReport();
/* 126:    */     }
/* 127:    */     catch (JRException e)
/* 128:    */     {
/* 129:125 */       LOG.info("Error JRException");
/* 130:126 */       e.printStackTrace();
/* 131:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 132:    */     }
/* 133:    */     catch (IOException e)
/* 134:    */     {
/* 135:129 */       LOG.info("Error IOException");
/* 136:130 */       e.printStackTrace();
/* 137:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 138:    */     }
/* 139:134 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Departamento getDepartamento()
/* 143:    */   {
/* 144:138 */     return this.departamento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDepartamento(Departamento departamento)
/* 148:    */   {
/* 149:142 */     this.departamento = departamento;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Sucursal getSucursal()
/* 153:    */   {
/* 154:146 */     return this.sucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setSucursal(Sucursal sucursal)
/* 158:    */   {
/* 159:150 */     this.sucursal = sucursal;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Genero getGenero()
/* 163:    */   {
/* 164:154 */     return this.genero;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setGenero(Genero genero)
/* 168:    */   {
/* 169:158 */     this.genero = genero;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public enumTipoReporte getTipoReporte()
/* 173:    */   {
/* 174:162 */     return this.tipoReporte;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 178:    */   {
/* 179:166 */     this.tipoReporte = tipoReporte;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<SelectItem> getListaTipoReporte()
/* 183:    */   {
/* 184:170 */     if (this.listaTipoReporte == null)
/* 185:    */     {
/* 186:171 */       this.listaTipoReporte = new ArrayList();
/* 187:172 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 188:173 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 189:    */       }
/* 190:    */     }
/* 191:177 */     return this.listaTipoReporte;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 195:    */   {
/* 196:181 */     this.listaTipoReporte = listaTipoReporte;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Date getFechaDesde()
/* 200:    */   {
/* 201:185 */     return this.fechaDesde;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFechaDesde(Date fechaDesde)
/* 205:    */   {
/* 206:189 */     this.fechaDesde = fechaDesde;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Date getFechaHasta()
/* 210:    */   {
/* 211:193 */     return this.fechaHasta;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setFechaHasta(Date fechaHasta)
/* 215:    */   {
/* 216:197 */     this.fechaHasta = fechaHasta;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<Departamento> getListaDepartamento()
/* 220:    */   {
/* 221:201 */     if (this.listaDepartamento == null) {
/* 222:202 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 223:    */     }
/* 224:204 */     return this.listaDepartamento;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 228:    */   {
/* 229:208 */     this.listaDepartamento = listaDepartamento;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<Sucursal> getListaSucursal()
/* 233:    */   {
/* 234:217 */     if (this.listaSucursal == null) {
/* 235:218 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 236:    */     }
/* 237:220 */     return this.listaSucursal;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 241:    */   {
/* 242:224 */     this.listaSucursal = listaSucursal;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public enumEstado getEstado()
/* 246:    */   {
/* 247:231 */     return this.estado;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setEstado(enumEstado estado)
/* 251:    */   {
/* 252:238 */     this.estado = estado;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public List<SelectItem> getListaEstado()
/* 256:    */   {
/* 257:245 */     if (this.listaEstado == null)
/* 258:    */     {
/* 259:246 */       this.listaEstado = new ArrayList();
/* 260:247 */       for (enumEstado es : enumEstado.values()) {
/* 261:248 */         this.listaEstado.add(new SelectItem(es, es.name()));
/* 262:    */       }
/* 263:    */     }
/* 264:252 */     return this.listaEstado;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setListaEstado(List<SelectItem> listaEstado)
/* 268:    */   {
/* 269:259 */     this.listaEstado = listaEstado;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<SelectItem> getListaMes()
/* 273:    */   {
/* 274:263 */     if (this.listaMes == null)
/* 275:    */     {
/* 276:264 */       this.listaMes = new ArrayList();
/* 277:265 */       for (Mes t : Mes.values())
/* 278:    */       {
/* 279:266 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 280:267 */         this.listaMes.add(item);
/* 281:    */       }
/* 282:    */     }
/* 283:271 */     return this.listaMes;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setListaMes(List<SelectItem> listaMes)
/* 287:    */   {
/* 288:275 */     this.listaMes = listaMes;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public int getMes()
/* 292:    */   {
/* 293:279 */     return this.mes;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setMes(int mes)
/* 297:    */   {
/* 298:283 */     this.mes = mes;
/* 299:    */   }
/* 300:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteEmpleadosBean
 * JD-Core Version:    0.7.0.1
 */