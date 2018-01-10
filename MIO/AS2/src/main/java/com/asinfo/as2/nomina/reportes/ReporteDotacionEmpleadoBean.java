/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteDotacionEmpleadoBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioReporteNomina servicioReporteNomina;
/*  35:    */   @EJB
/*  36:    */   private ServicioDepartamento servicioDepartamento;
/*  37:    */   private Empleado empleado;
/*  38:    */   private Departamento departamento;
/*  39:    */   private Producto producto;
/*  40:    */   private Date fechaDesde;
/*  41:    */   private Date fechaHasta;
/*  42:    */   @NotNull
/*  43:    */   private TipoReporteEnum tipoReporteEnum;
/*  44:    */   private List<SelectItem> listaTipoReporteEnum;
/*  45:    */   private List<Departamento> listaDepartamento;
/*  46:    */   
/*  47:    */   private static enum TipoReporteEnum
/*  48:    */   {
/*  49: 53 */     DOTACION_EMPLEADO_DETALLADO("Dotación Empleado Detallado"),  DOTACION_EMPLEADO_RESUMIDO("Dotación Empleado Resumido"),  REPOSICION_DOTACION("Reposición Dotación");
/*  50:    */     
/*  51:    */     private String nombre;
/*  52:    */     
/*  53:    */     private TipoReporteEnum(String nombre)
/*  54:    */     {
/*  55: 58 */       this.nombre = nombre;
/*  56:    */     }
/*  57:    */     
/*  58:    */     public String getNombre()
/*  59:    */     {
/*  60: 62 */       return this.nombre;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void setNombre(String nombre)
/*  64:    */     {
/*  65: 67 */       this.nombre = nombre;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected JRDataSource getJRDataSource()
/*  70:    */   {
/*  71: 80 */     List listaDatosReporte = new ArrayList();
/*  72: 81 */     JRDataSource ds = null;
/*  73: 82 */     String[] fields = null;
/*  74:    */     try
/*  75:    */     {
/*  76: 85 */       int idEmpleado = this.empleado == null ? 0 : this.empleado.getId();
/*  77: 86 */       int idDepartamento = this.departamento == null ? 0 : this.departamento.getId();
/*  78: 87 */       int idProducto = this.producto == null ? 0 : this.producto.getId();
/*  79:    */       
/*  80: 89 */       boolean reposicionDotacion = this.tipoReporteEnum == TipoReporteEnum.REPOSICION_DOTACION;
/*  81: 90 */       boolean dotacionEmpleadoDetallado = this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_DETALLADO;
/*  82: 91 */       boolean dotacionEmpleadoResumido = this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_RESUMIDO;
/*  83:    */       
/*  84: 93 */       listaDatosReporte = this.servicioReporteNomina.getReporteDotacionEmpleado(this.fechaDesde, this.fechaHasta, idEmpleado, idProducto, idDepartamento, AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), dotacionEmpleadoDetallado, dotacionEmpleadoResumido, reposicionDotacion);
/*  85: 95 */       if ((dotacionEmpleadoDetallado) || (reposicionDotacion)) {
/*  86: 97 */         fields = new String[] { "f_nombres", "f_apellidos", "f_fechaEntrega", "f_fechaReposicion", "f_codigoProducto", "f_producto", "f_departamento", "f_cantidad", "f_descripcion", "f_totalCantidad" };
/*  87: 99 */       } else if (dotacionEmpleadoResumido) {
/*  88:100 */         fields = new String[] { "f_codigoProducto", "f_producto", "f_departamento", "f_totalCantidad" };
/*  89:    */       }
/*  90:104 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:107 */       LOG.info("Error " + e);
/*  95:108 */       e.printStackTrace();
/*  96:    */     }
/*  97:110 */     return ds;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @PostConstruct
/* 101:    */   public void init()
/* 102:    */   {
/* 103:116 */     Calendar calfechaDesde = Calendar.getInstance();
/* 104:117 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 105:118 */     this.fechaDesde = calfechaDesde.getTime();
/* 106:119 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected String getCompileFileName()
/* 110:    */   {
/* 111:129 */     if (this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_DETALLADO) {
/* 112:130 */       return "reporteDotacionEmpleado";
/* 113:    */     }
/* 114:131 */     if (this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_RESUMIDO) {
/* 115:132 */       return "reporteDotacionEmpleadoResumido";
/* 116:    */     }
/* 117:133 */     if (this.tipoReporteEnum == TipoReporteEnum.REPOSICION_DOTACION) {
/* 118:134 */       return "reporteReposicionDotacion";
/* 119:    */     }
/* 120:137 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected Map<String, Object> getReportParameters()
/* 124:    */   {
/* 125:144 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 126:145 */     if (this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_DETALLADO) {
/* 127:146 */       reportParameters.put("ReportTitle", "Reporte Dotación Empleado Detallado");
/* 128:147 */     } else if (this.tipoReporteEnum == TipoReporteEnum.REPOSICION_DOTACION) {
/* 129:148 */       reportParameters.put("ReportTitle", "Reporte Reposición Dotación");
/* 130:149 */     } else if (this.tipoReporteEnum == TipoReporteEnum.DOTACION_EMPLEADO_RESUMIDO) {
/* 131:150 */       reportParameters.put("ReportTitle", "Reporte Dotación Empleado Resumido");
/* 132:    */     }
/* 133:152 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 134:153 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 135:    */     
/* 136:155 */     reportParameters.put("p_departamentos", this.departamento == null ? "Todos" : Integer.valueOf(this.departamento.getId()));
/* 137:156 */     reportParameters.put("p_empleado", this.empleado != null ? this.empleado.getNombreCompleto() : "Todos");
/* 138:157 */     reportParameters.put("p_producto", this.producto != null ? this.producto.getNombreComercial() : "Todos");
/* 139:    */     
/* 140:159 */     return reportParameters;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String execute()
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:164 */       super.prepareReport();
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:166 */       e.printStackTrace();
/* 152:    */     }
/* 153:169 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String cargarEmpleado()
/* 157:    */   {
/* 158:173 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void cargarProducto() {}
/* 162:    */   
/* 163:    */   public Empleado getEmpleado()
/* 164:    */   {
/* 165:181 */     if (this.empleado == null) {
/* 166:182 */       this.empleado = new Empleado();
/* 167:    */     }
/* 168:184 */     return this.empleado;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setEmpleado(Empleado empleado)
/* 172:    */   {
/* 173:188 */     this.empleado = empleado;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Departamento getDepartamento()
/* 177:    */   {
/* 178:192 */     if (this.departamento == null) {
/* 179:193 */       this.departamento = new Departamento();
/* 180:    */     }
/* 181:195 */     return this.departamento;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setDepartamento(Departamento departamento)
/* 185:    */   {
/* 186:199 */     this.departamento = departamento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<Departamento> getListaDepartamento()
/* 190:    */   {
/* 191:204 */     if (this.listaDepartamento == null) {
/* 192:205 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 193:    */     }
/* 194:207 */     return this.listaDepartamento;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Date getFechaDesde()
/* 198:    */   {
/* 199:211 */     return this.fechaDesde;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setFechaDesde(Date fechaDesde)
/* 203:    */   {
/* 204:215 */     this.fechaDesde = fechaDesde;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Date getFechaHasta()
/* 208:    */   {
/* 209:219 */     return this.fechaHasta;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setFechaHasta(Date fechaHasta)
/* 213:    */   {
/* 214:223 */     this.fechaHasta = fechaHasta;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Producto getProducto()
/* 218:    */   {
/* 219:228 */     if (this.producto == null) {
/* 220:229 */       this.producto = new Producto();
/* 221:    */     }
/* 222:231 */     return this.producto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setProducto(Producto producto)
/* 226:    */   {
/* 227:235 */     this.producto = producto;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public TipoReporteEnum getTipoReporteEnum()
/* 231:    */   {
/* 232:240 */     return this.tipoReporteEnum;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setTipoReporteEnum(TipoReporteEnum tipoReporteEnum)
/* 236:    */   {
/* 237:244 */     this.tipoReporteEnum = tipoReporteEnum;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<SelectItem> getListaTipoReporteEnum()
/* 241:    */   {
/* 242:248 */     if (this.listaTipoReporteEnum == null)
/* 243:    */     {
/* 244:249 */       this.listaTipoReporteEnum = new ArrayList();
/* 245:250 */       for (TipoReporteEnum tipoReporteEnum : TipoReporteEnum.values())
/* 246:    */       {
/* 247:251 */         SelectItem selectItem = new SelectItem(tipoReporteEnum, tipoReporteEnum.getNombre());
/* 248:252 */         this.listaTipoReporteEnum.add(selectItem);
/* 249:    */       }
/* 250:    */     }
/* 251:255 */     return this.listaTipoReporteEnum;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaTipoReporteEnum(List<SelectItem> listaTipoReporteEnum)
/* 255:    */   {
/* 256:260 */     this.listaTipoReporteEnum = listaTipoReporteEnum;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteDotacionEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */