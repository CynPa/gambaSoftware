/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   6:    */ import com.asinfo.as2.entities.Departamento;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo;
/*  12:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.RequestScoped;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import net.sf.jasperreports.engine.JRException;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @RequestScoped
/*  30:    */ public class ReportePrestamoEmpleadoBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = -7306672667528700536L;
/*  34:    */   @EJB
/*  35:    */   private ServicioReporteNomina servicioReporteNomina;
/*  36:    */   @EJB
/*  37:    */   private ServicioPagoRol servicioPagoRol;
/*  38:    */   @EJB
/*  39:    */   private ServicioSucursal servicioSucursal;
/*  40:    */   @EJB
/*  41:    */   private ServicioTipoPrestamo servicioTipoPrestamo;
/*  42:    */   @EJB
/*  43:    */   private ServicioDepartamento servicioDepartamento;
/*  44:    */   private TipoPrestamo tipoPrestamo;
/*  45:    */   private Departamento departamento;
/*  46:    */   private Empleado empleado;
/*  47:    */   private Sucursal sucursal;
/*  48: 72 */   private boolean indicadorActivos = true;
/*  49:    */   private Date fechaDesde;
/*  50:    */   private Date fechaHasta;
/*  51:    */   private List<Sucursal> listaSucursal;
/*  52:    */   private List<TipoPrestamo> listaTipoPrestamo;
/*  53:    */   private List<Departamento> listaDepartamento;
/*  54:    */   
/*  55:    */   protected JRDataSource getJRDataSource()
/*  56:    */   {
/*  57: 91 */     List listaDatosReporte = new ArrayList();
/*  58: 92 */     JRDataSource ds = null;
/*  59: 93 */     listaDatosReporte = this.servicioReporteNomina.getReportePrestamoEmpleado(AppUtil.getOrganizacion()
/*  60: 94 */       .getIdOrganizacion(), this.tipoPrestamo, this.departamento, this.empleado, this.sucursal, this.indicadorActivos, this.fechaDesde, this.fechaHasta);
/*  61: 95 */     String[] fields = { "f_tipoPrestamo", "f_idPrestamo", "f_fechaSolicitud", "f_fechaAprobacion", "f_fechaInicioDescuento", "f_monto", "f_plazo", "f_identificacion", "f_nombres", "f_apellidos", "f_numeroCuota", "f_fechaCuota", "f_valorCuota", "f_pagoCuota", "f_saldo" };
/*  62:    */     
/*  63:    */ 
/*  64: 98 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  65:    */     
/*  66:100 */     return ds;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Map<String, Object> getReportParameters()
/*  70:    */   {
/*  71:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  72:112 */     reportParameters.put("ReportTitle", "Reporte de Prestamos de Empleados");
/*  73:113 */     reportParameters.put("p_sucursal", this.sucursal != null ? getSucursal().getNombre() : "Todos");
/*  74:114 */     reportParameters.put("p_departamento", this.departamento != null ? getDepartamento().getNombre() : "Todos");
/*  75:    */     
/*  76:116 */     return reportParameters;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String execute()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:126 */       super.prepareReport();
/*  84:    */     }
/*  85:    */     catch (JRException e)
/*  86:    */     {
/*  87:128 */       LOG.info("Error JRException");
/*  88:129 */       e.printStackTrace();
/*  89:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:132 */       LOG.info("Error IOException");
/*  94:133 */       e.printStackTrace();
/*  95:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:137 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String cargarEmpleado()
/* 101:    */   {
/* 102:141 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected String getCompileFileName()
/* 106:    */   {
/* 107:152 */     return "reportePrestamoEmpleado";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Empleado getEmpleado()
/* 111:    */   {
/* 112:162 */     return this.empleado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setEmpleado(Empleado empleado)
/* 116:    */   {
/* 117:172 */     this.empleado = empleado;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Sucursal getSucursal()
/* 121:    */   {
/* 122:181 */     return this.sucursal;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setSucursal(Sucursal sucursal)
/* 126:    */   {
/* 127:191 */     this.sucursal = sucursal;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<Sucursal> getListaSucursal()
/* 131:    */   {
/* 132:200 */     if (this.listaSucursal == null) {
/* 133:201 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 134:    */     }
/* 135:203 */     return this.listaSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 139:    */   {
/* 140:213 */     this.listaSucursal = listaSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isIndicadorActivos()
/* 144:    */   {
/* 145:220 */     return this.indicadorActivos;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setIndicadorActivos(boolean indicadorActivos)
/* 149:    */   {
/* 150:228 */     this.indicadorActivos = indicadorActivos;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public TipoPrestamo getTipoPrestamo()
/* 154:    */   {
/* 155:235 */     return this.tipoPrestamo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setTipoPrestamo(TipoPrestamo tipoPrestamo)
/* 159:    */   {
/* 160:243 */     this.tipoPrestamo = tipoPrestamo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Departamento getDepartamento()
/* 164:    */   {
/* 165:250 */     return this.departamento;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setDepartamento(Departamento departamento)
/* 169:    */   {
/* 170:258 */     this.departamento = departamento;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List<TipoPrestamo> getListaTipoPrestamo()
/* 174:    */   {
/* 175:265 */     if (this.listaTipoPrestamo == null) {
/* 176:266 */       this.listaTipoPrestamo = this.servicioTipoPrestamo.obtenerListaCombo("nombre", true, null);
/* 177:    */     }
/* 178:269 */     return this.listaTipoPrestamo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setListaTipoPrestamo(List<TipoPrestamo> listaTipoPrestamo)
/* 182:    */   {
/* 183:276 */     this.listaTipoPrestamo = listaTipoPrestamo;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Departamento> getListaDepartamento()
/* 187:    */   {
/* 188:283 */     if (this.listaDepartamento == null) {
/* 189:284 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 190:    */     }
/* 191:287 */     return this.listaDepartamento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaDepartamento(List<Departamento> listaDepartamento)
/* 195:    */   {
/* 196:294 */     this.listaDepartamento = listaDepartamento;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Date getFechaDesde()
/* 200:    */   {
/* 201:298 */     return this.fechaDesde;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFechaDesde(Date fechaDesde)
/* 205:    */   {
/* 206:302 */     this.fechaDesde = fechaDesde;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Date getFechaHasta()
/* 210:    */   {
/* 211:306 */     return this.fechaHasta;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setFechaHasta(Date fechaHasta)
/* 215:    */   {
/* 216:310 */     this.fechaHasta = fechaHasta;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReportePrestamoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */