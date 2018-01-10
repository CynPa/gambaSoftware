/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   7:    */ import com.asinfo.as2.entities.Recaudador;
/*   8:    */ import com.asinfo.as2.entities.Subempresa;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Zona;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteAnalisisVencimientoClienteBean
/*  32:    */   extends AbstractClientReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 2246970453430672098L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteVenta servicioReporteVenta;
/*  37:    */   private int numeroDia;
/*  38:    */   
/*  39:    */   protected JRDataSource getJRDataSource()
/*  40:    */   {
/*  41: 63 */     List listaDatosReporte = new ArrayList();
/*  42: 64 */     JRDataSource ds = null;
/*  43:    */     try
/*  44:    */     {
/*  45: 66 */       listaDatosReporte = this.servicioReporteVenta.getAnalisisVencimientoCliente(getFechaHasta(), this.empresa, this.recaudador, AppUtil.getOrganizacion()
/*  46: 67 */         .getId(), this.subempresa, this.agenteComercial, this.sucursal, this.puntoVenta, this.zona, this.numeroDia);
/*  47:    */       
/*  48: 69 */       String[] fields = { "codigoEmpresa", "nombreFiscal", "codigoTipoIdentificacion", "identificacion", "numeroFactura", "fechaFactura", "fechaVencimiento", "diasPlazoFactura", "agenteComercial", "empresaFinal", "f_total_factura", "f_saldo_factura", "f_vencido120+", "f_vencido120", "f_vencido90", "f_vencido60", "f_vencido30", "f_vencidoMenosDias", "f_por_vencer30", "f_porVencerMasDias", "f_por_vencer60", "f_por_vencer90", "f_por_vencer120", "f_por_vencer120+", "f_total", "codigoDocumento", "f_valorBloqueado", "f_valorCredito", "numero_packing", "f_consignatario" };
/*  49:    */       
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 74 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  54:    */     }
/*  55:    */     catch (ExcepcionAS2 e)
/*  56:    */     {
/*  57: 76 */       LOG.info("Error " + e);
/*  58: 77 */       e.printStackTrace();
/*  59:    */     }
/*  60: 79 */     return ds;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected Map<String, Object> getReportParameters()
/*  64:    */   {
/*  65: 89 */     if (null == this.empresa)
/*  66:    */     {
/*  67: 90 */       this.empresa = new Empresa();
/*  68: 91 */       this.empresa.setNombreFiscal("");
/*  69:    */     }
/*  70: 94 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  71: 95 */     if (this.indicadorResumen)
/*  72:    */     {
/*  73: 96 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_analisis_vencimientos_resumido_titulo"));
/*  74: 97 */       reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 99 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_analisis_vencimientos_titulo"));
/*  79:100 */       reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  80:    */     }
/*  81:103 */     reportParameters.put("reporteCliente", Boolean.TRUE);
/*  82:    */     
/*  83:105 */     reportParameters
/*  84:106 */       .put("agenteComercial", this.agenteComercial != null ? this.agenteComercial.getNombre1() + " " + this.agenteComercial.getNombre2() : "Todos");
/*  85:107 */     reportParameters.put("p_subempresa", this.subempresa != null ? this.subempresa.getEmpresaFinal() : "Todos");
/*  86:108 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/*  87:109 */     reportParameters.put("p_recaudador", this.recaudador != null ? this.recaudador.getNombre() : "Todos");
/*  88:110 */     reportParameters.put("p_puntoVenta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/*  89:111 */     reportParameters.put("p_empresa", this.empresa.getId() != 0 ? this.empresa.getNombreFiscal() : "Todos");
/*  90:112 */     reportParameters.put("p_fechaHasta", getFechaHasta());
/*  91:113 */     reportParameters.put("p_zona", this.zona != null ? this.zona.getNombre() : "Todos");
/*  92:114 */     reportParameters.put("p_numeroDia", Integer.toString(getNumeroDia()));
/*  93:    */     
/*  94:116 */     return reportParameters;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String execute()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:126 */       super.prepareReport();
/* 102:    */     }
/* 103:    */     catch (JRException e)
/* 104:    */     {
/* 105:128 */       LOG.info("Error JRException");
/* 106:129 */       e.printStackTrace();
/* 107:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 108:    */     }
/* 109:    */     catch (IOException e)
/* 110:    */     {
/* 111:132 */       LOG.info("Error IOException");
/* 112:133 */       e.printStackTrace();
/* 113:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 114:    */     }
/* 115:137 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected String getCompileFileName()
/* 119:    */   {
/* 120:147 */     if (this.indicadorResumen) {
/* 121:148 */       return "reporteAnalisisVencimientoResumido";
/* 122:    */     }
/* 123:150 */     return "reporteAnalisisVencimientoDetallado";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getNumeroDia()
/* 127:    */   {
/* 128:155 */     return this.numeroDia == 0 ? 10 : this.numeroDia;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNumeroDia(int numeroDia)
/* 132:    */   {
/* 133:159 */     this.numeroDia = numeroDia;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteAnalisisVencimientoClienteBean
 * JD-Core Version:    0.7.0.1
 */