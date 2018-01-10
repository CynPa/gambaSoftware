/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.RequestScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @RequestScoped
/*  25:    */ public class ReporteChequeProtestadoBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -326616192156976012L;
/*  29:    */   @EJB
/*  30:    */   private transient ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioEmpresa servicioEmpresa;
/*  35:    */   private Date fechaDesde;
/*  36:    */   private Date fechaHasta;
/*  37:    */   private Empresa empresa;
/*  38:    */   private boolean indicadorSaldoDiferenciaCero;
/*  39:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  40:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 71 */     JRDataSource ds = null;
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48: 75 */     List listaDatosReporte = this.servicioReporteCobroCliente.getReporteChequesProtestados(this.fechaDesde, this.fechaHasta, this.empresa, this.cuentaBancariaOrganizacion, this.indicadorSaldoDiferenciaCero);
/*  49: 78 */     if (listaDatosReporte.size() == 0)
/*  50:    */     {
/*  51: 79 */       addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 82 */       String[] fields = { "f_vendedor", "f_nombreFiscal", "f_nombreComercial", "f_identificacion", "f_numeroCobro", "f_fechaProtesto", "f_documentoReferencia", "f_descripcion", "f_valor", "f_saldo", "f_valorBloqueado", "f_banco", "indicadorAnulada" };
/*  56:    */       
/*  57:    */ 
/*  58: 85 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  59:    */     }
/*  60: 93 */     return ds;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getCompileFileName()
/*  64:    */   {
/*  65:104 */     return "reporteChequeProtestado";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Map<String, Object> getReportParameters()
/*  69:    */   {
/*  70:116 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  71:117 */     reportParameters.put("ReportTitle", "Reporte de Cheques Protestados");
/*  72:118 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  73:119 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  74:120 */     return reportParameters;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Date getFechaHasta()
/*  78:    */   {
/*  79:129 */     return this.fechaHasta;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setFechaHasta(Date fechaHasta)
/*  83:    */   {
/*  84:139 */     this.fechaHasta = fechaHasta;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String execute()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:150 */       if (this.fechaHasta == null) {
/*  92:151 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/*  93:    */       }
/*  94:153 */       super.prepareReport();
/*  95:    */     }
/*  96:    */     catch (JRException e)
/*  97:    */     {
/*  98:155 */       LOG.info("Error JRException");
/*  99:156 */       e.printStackTrace();
/* 100:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:    */     catch (IOException e)
/* 103:    */     {
/* 104:159 */       LOG.info("Error IOException");
/* 105:160 */       e.printStackTrace();
/* 106:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:164 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 112:    */   {
/* 113:168 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Empresa getEmpresa()
/* 117:    */   {
/* 118:177 */     if (this.empresa == null) {
/* 119:178 */       this.empresa = new Empresa();
/* 120:    */     }
/* 121:180 */     return this.empresa;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setEmpresa(Empresa empresa)
/* 125:    */   {
/* 126:190 */     this.empresa = empresa;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isIndicadorSaldoDiferenciaCero()
/* 130:    */   {
/* 131:199 */     return this.indicadorSaldoDiferenciaCero;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIndicadorSaldoDiferenciaCero(boolean indicadorSaldoDiferenciaCero)
/* 135:    */   {
/* 136:209 */     this.indicadorSaldoDiferenciaCero = indicadorSaldoDiferenciaCero;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 140:    */   {
/* 141:218 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 142:219 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/* 143:    */     }
/* 144:221 */     return this.listaCuentaBancariaOrganizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 148:    */   {
/* 149:225 */     return this.cuentaBancariaOrganizacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 153:    */   {
/* 154:229 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFechaDesde()
/* 158:    */   {
/* 159:233 */     return this.fechaDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFechaDesde(Date fechaDesde)
/* 163:    */   {
/* 164:237 */     this.fechaDesde = fechaDesde;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteChequeProtestadoBean
 * JD-Core Version:    0.7.0.1
 */