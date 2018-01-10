/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.DimensionContable;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   9:    */ import com.asinfo.as2.entities.Recaudador;
/*  10:    */ import com.asinfo.as2.entities.Subempresa;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Zona;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteCorteFechaClienteBean
/*  35:    */   extends AbstractClientReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -744595516610343054L;
/*  38:    */   @EJB
/*  39:    */   private ServicioReporteVenta servicioReporteVenta;
/*  40:    */   private DimensionContable proyecto;
/*  41:    */   private CategoriaEmpresa categoriaEmpresa;
/*  42:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  43:    */   
/*  44:    */   protected String getCompileFileName()
/*  45:    */   {
/*  46: 68 */     if (this.indicadorResumen) {
/*  47: 69 */       return "reporteCorteResumido";
/*  48:    */     }
/*  49: 71 */     return "reporteCorte";
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected Map<String, Object> getReportParameters()
/*  53:    */   {
/*  54: 82 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  55: 83 */     if (this.indicadorResumen) {
/*  56: 84 */       reportParameters.put("ReportTitle", 
/*  57: 85 */         getLanguageController().getMensaje("msg_corte_fecha_resumido_titulo"));
/*  58:    */     } else {
/*  59: 88 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_corte_fecha_titulo"));
/*  60:    */     }
/*  61: 90 */     if (this.fechaHasta == null) {
/*  62: 91 */       this.fechaHasta = new Date();
/*  63:    */     }
/*  64: 93 */     reportParameters.put("reporteCliente", Boolean.TRUE);
/*  65: 94 */     reportParameters.put("FechaHasta", this.fechaHasta);
/*  66: 95 */     reportParameters.put("total", "Total");
/*  67: 96 */     reportParameters
/*  68: 97 */       .put("agenteComercial", this.agenteComercial != null ? this.agenteComercial.getNombre1() + " " + this.agenteComercial.getNombre2() : "Todos");
/*  69: 98 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/*  70: 99 */     reportParameters.put("p_recaudador", this.recaudador != null ? this.recaudador.getNombre() : "Todos");
/*  71:100 */     reportParameters.put("p_puntoVenta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/*  72:101 */     reportParameters.put("p_subempresa", this.subempresa != null ? this.subempresa.getEmpresaFinal() : "Todos");
/*  73:102 */     reportParameters.put("p_zona", this.zona != null ? this.zona.getNombre() : "Todos");
/*  74:103 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? this.categoriaEmpresa.getNombre() : "Todos");
/*  75:    */     
/*  76:105 */     return reportParameters;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected JRDataSource getJRDataSource()
/*  80:    */   {
/*  81:116 */     List listaDatosReporte = new ArrayList();
/*  82:117 */     JRDataSource ds = null;
/*  83:    */     try
/*  84:    */     {
/*  85:121 */       listaDatosReporte = this.servicioReporteVenta.getListaReporteCorteFecha(this.fechaHasta, this.empresa, this.recaudador, 
/*  86:122 */         AppUtil.getOrganizacion().getId(), this.subempresa, this.agenteComercial, this.sucursal, this.puntoVenta, this.zona, this.proyecto, this.categoriaEmpresa);
/*  87:123 */       String[] fields = { "codigo", "identificacion", "nombre", "fechaFactura", "factura", "debito", "credito", "saldo", "indicadorGeneradaProtesto", "codigoDocumento", "numero_packing", "proyecto" };
/*  88:    */       
/*  89:    */ 
/*  90:126 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  91:    */     }
/*  92:    */     catch (ExcepcionAS2 e)
/*  93:    */     {
/*  94:128 */       e.printStackTrace();
/*  95:129 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  96:    */     }
/*  97:131 */     return ds;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String execute()
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:141 */       if (this.fechaHasta == null) {
/* 105:142 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 106:    */       }
/* 107:144 */       super.prepareReport();
/* 108:    */     }
/* 109:    */     catch (JRException e)
/* 110:    */     {
/* 111:146 */       LOG.info("Error JRException");
/* 112:147 */       e.printStackTrace();
/* 113:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 114:    */     }
/* 115:    */     catch (IOException e)
/* 116:    */     {
/* 117:150 */       LOG.info("Error IOException");
/* 118:151 */       e.printStackTrace();
/* 119:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:155 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public DimensionContable getProyecto()
/* 125:    */   {
/* 126:159 */     return this.proyecto;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setProyecto(DimensionContable proyecto)
/* 130:    */   {
/* 131:163 */     this.proyecto = proyecto;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 135:    */   {
/* 136:167 */     return this.categoriaEmpresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 140:    */   {
/* 141:171 */     this.categoriaEmpresa = categoriaEmpresa;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 145:    */   {
/* 146:175 */     if (this.listaCategoriaEmpresa == null) {
/* 147:176 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 148:    */     }
/* 149:178 */     return this.listaCategoriaEmpresa;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCorteFechaClienteBean
 * JD-Core Version:    0.7.0.1
 */