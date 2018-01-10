/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   5:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteMovimientoBancario;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReporteChequesGiradosNoCobradosBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -8575219747524044522L;
/*  29:    */   @EJB
/*  30:    */   private ServicioReporteMovimientoBancario servicioReporteMovimientoBancario;
/*  31:    */   @EJB
/*  32:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  33:    */   private Date fechaDesde;
/*  34:    */   private Date fechaHasta;
/*  35:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  36:    */   List<Object[]> listaDatosReporte;
/*  37:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo;
/*  38:    */   
/*  39:    */   protected JRDataSource getJRDataSource()
/*  40:    */   {
/*  41: 62 */     JRDataSource ds = null;
/*  42:    */     try
/*  43:    */     {
/*  44: 68 */       String[] fields = { "f_idMovimientoBancario", "f_banco", "f_numeroCuenta", "f_proveedorBeneficiario", "f_cheque", "f_fecha", "f_factura", "f_valor", "f_valorFactura" };
/*  45:    */       
/*  46:    */ 
/*  47: 71 */       ds = new QueryResultDataSource(this.listaDatosReporte, fields);
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 73 */       LOG.info("Error " + e);
/*  52: 74 */       e.printStackTrace();
/*  53:    */     }
/*  54: 76 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 86 */     return "reporteChequesGiradosNoCobrados";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64: 97 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65: 98 */     reportParameters.put("ReportTitle", "ReporteCheques Girados No Cobrados");
/*  66: 99 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  67:100 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  68:101 */     reportParameters.put("totalMonto", calculaTotalMonto().negate());
/*  69:102 */     return reportParameters;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String execute()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:108 */       validarDatos();
/*  77:    */       
/*  78:110 */       this.listaDatosReporte = this.servicioReporteMovimientoBancario.getReporteChequesGiradosNoCobrados(this.fechaDesde, this.fechaHasta, this.cuentaBancariaOrganizacion);
/*  79:    */       
/*  80:    */ 
/*  81:113 */       calculaTotalMonto();
/*  82:    */       
/*  83:115 */       super.prepareReport();
/*  84:    */     }
/*  85:    */     catch (JRException e)
/*  86:    */     {
/*  87:118 */       LOG.info("Error JRException");
/*  88:119 */       e.printStackTrace();
/*  89:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:122 */       LOG.info("Error IOException");
/*  94:123 */       e.printStackTrace();
/*  95:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:127 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void validarDatos()
/* 101:    */   {
/* 102:131 */     if (this.fechaDesde == null) {
/* 103:132 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 104:    */     }
/* 105:134 */     if (this.fechaHasta == null) {
/* 106:135 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public BigDecimal calculaTotalMonto()
/* 111:    */   {
/* 112:140 */     HashMap<Integer, Integer> hmEmpresa = new HashMap();
/* 113:    */     
/* 114:142 */     BigDecimal totalMonto = BigDecimal.ZERO;
/* 115:144 */     for (Object[] objects : this.listaDatosReporte) {
/* 116:145 */       if (!hmEmpresa.containsKey((Integer)objects[0]))
/* 117:    */       {
/* 118:146 */         totalMonto = totalMonto.add((BigDecimal)objects[7]);
/* 119:147 */         hmEmpresa.put((Integer)objects[0], (Integer)objects[0]);
/* 120:    */       }
/* 121:    */     }
/* 122:150 */     return totalMonto;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getFechaDesde()
/* 126:    */   {
/* 127:159 */     return this.fechaDesde;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setFechaDesde(Date fechaDesde)
/* 131:    */   {
/* 132:169 */     this.fechaDesde = fechaDesde;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Date getFechaHasta()
/* 136:    */   {
/* 137:178 */     return this.fechaHasta;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFechaHasta(Date fechaHasta)
/* 141:    */   {
/* 142:188 */     this.fechaHasta = fechaHasta;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 146:    */   {
/* 147:197 */     return this.cuentaBancariaOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 151:    */   {
/* 152:207 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionCombo()
/* 156:    */   {
/* 157:216 */     if (this.listaCuentaBancariaOrganizacionCombo == null) {
/* 158:217 */       this.listaCuentaBancariaOrganizacionCombo = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, true, null);
/* 159:    */     }
/* 160:219 */     return this.listaCuentaBancariaOrganizacionCombo;
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteChequesGiradosNoCobradosBean
 * JD-Core Version:    0.7.0.1
 */