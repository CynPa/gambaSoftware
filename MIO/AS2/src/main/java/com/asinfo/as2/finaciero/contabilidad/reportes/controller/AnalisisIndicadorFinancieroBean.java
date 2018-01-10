/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.IndicadorFinanciero;
/*   5:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioIndicadorFinanciero;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class AnalisisIndicadorFinancieroBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioIndicadorFinanciero servicioIndicadorFinanciero;
/*  31:    */   @EJB
/*  32:    */   private ServicioVariable servicioVariable;
/*  33:    */   private int mesDesde;
/*  34:    */   private int mesHasta;
/*  35:    */   private int annio;
/*  36: 69 */   private final String COMPILE_FILE_NAME = "reporteIndicadorFinanciero";
/*  37:    */   private IndicadorFinanciero indicadorFinanciero;
/*  38:    */   private List<IndicadorFinanciero> listaIndicadorFinancieroConValorDeExpresion;
/*  39:    */   private List<SelectItem> listaMes;
/*  40:    */   private List<IndicadorFinanciero> listaIndicadorFinancieroSeleccionados;
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 80 */     List listaDatosReporte = new ArrayList();
/*  45: 81 */     JRDataSource ds = null;
/*  46: 82 */     String[] fields = null;
/*  47:    */     try
/*  48:    */     {
/*  49: 85 */       ds = this.servicioIndicadorFinanciero.getReporteIndicadorFinanciero(this.listaIndicadorFinancieroSeleccionados, this.mesDesde, this.mesHasta, this.annio, this.annio);
/*  50: 86 */       fields = new String[] { "f_nombre", "listaDetalleIndicadorFinanciero" };
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 91 */       LOG.info("Error " + e);
/*  55: 92 */       e.printStackTrace();
/*  56:    */     }
/*  57: 94 */     return ds;
/*  58:    */   }
/*  59:    */   
/*  60:    */   @PostConstruct
/*  61:    */   public void init()
/*  62:    */   {
/*  63:101 */     this.mesDesde = FuncionesUtiles.obtenerMesActual();
/*  64:102 */     this.annio = FuncionesUtiles.obtenerAnioActual();
/*  65:103 */     this.mesHasta = FuncionesUtiles.obtenerMesActual();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void procesar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:111 */       this.listaIndicadorFinancieroConValorDeExpresion = new ArrayList();
/*  73:112 */       for (IndicadorFinanciero indicador : this.listaIndicadorFinancieroSeleccionados) {
/*  74:113 */         for (int i = this.mesDesde; i <= this.mesHasta; i++)
/*  75:    */         {
/*  76:114 */           Date fechaDesde = FuncionesUtiles.getFecha(1, i, this.annio);
/*  77:115 */           Date fechaHasta = FuncionesUtiles.getFechaFinMes(this.annio, i);
/*  78:116 */           IndicadorFinanciero indicadorFinancieroAux = new IndicadorFinanciero();
/*  79:117 */           IndicadorFinanciero indicadorFinanciero = this.servicioIndicadorFinanciero.calcularValorIndicadorFinanciero(indicador, fechaDesde, fechaHasta);
/*  80:118 */           indicadorFinancieroAux.setNombre(indicadorFinanciero.getNombre());
/*  81:119 */           indicadorFinancieroAux.setValor(indicadorFinanciero.getValor());
/*  82:120 */           indicadorFinancieroAux.setExpresion(indicadorFinanciero.getExpresion());
/*  83:121 */           indicadorFinancieroAux.setFecha(fechaHasta);
/*  84:122 */           this.listaIndicadorFinancieroConValorDeExpresion.add(indicadorFinancieroAux);
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_division_entre_0"));
/*  91:127 */       LOG.error("ERROR AL PROCESAR DATOS", e);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<IndicadorFinanciero> autocompletarIndicadorFinanciero(String consulta)
/*  96:    */   {
/*  97:135 */     HashMap<String, String> filters = new HashMap();
/*  98:136 */     filters.put("nombre", consulta.trim());
/*  99:137 */     List<IndicadorFinanciero> lista = this.servicioIndicadorFinanciero.obtenerListaCombo("", true, filters);
/* 100:138 */     return lista;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected String getCompileFileName()
/* 104:    */   {
/* 105:144 */     return "reporteIndicadorFinanciero";
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Map<String, Object> getReportParameters()
/* 109:    */   {
/* 110:150 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 111:151 */     reportParameters.put("ReportTitle", "Reporte Indicadores Financieros");
/* 112:152 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 113:    */     
/* 114:154 */     return reportParameters;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String execute()
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:159 */       super.prepareReport();
/* 122:    */     }
/* 123:    */     catch (Exception e)
/* 124:    */     {
/* 125:161 */       e.printStackTrace();
/* 126:    */     }
/* 127:164 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public IndicadorFinanciero getIndicadorFinanciero()
/* 131:    */   {
/* 132:168 */     return this.indicadorFinanciero;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIndicadorFinanciero(IndicadorFinanciero indicadorFinanciero)
/* 136:    */   {
/* 137:172 */     this.indicadorFinanciero = indicadorFinanciero;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getMesDesde()
/* 141:    */   {
/* 142:176 */     return this.mesDesde;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setMesDesde(int mesDesde)
/* 146:    */   {
/* 147:180 */     this.mesDesde = mesDesde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getMesHasta()
/* 151:    */   {
/* 152:184 */     return this.mesHasta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setMesHasta(int mesHasta)
/* 156:    */   {
/* 157:188 */     this.mesHasta = mesHasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<SelectItem> getListaMes()
/* 161:    */   {
/* 162:192 */     if (this.listaMes == null)
/* 163:    */     {
/* 164:193 */       this.listaMes = new ArrayList();
/* 165:194 */       for (Mes t : Mes.values())
/* 166:    */       {
/* 167:195 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 168:196 */         this.listaMes.add(item);
/* 169:    */       }
/* 170:    */     }
/* 171:199 */     return this.listaMes;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setListaMes(List<SelectItem> listaMes)
/* 175:    */   {
/* 176:203 */     this.listaMes = listaMes;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<IndicadorFinanciero> getListaIndicadorFinancieroConValorDeExpresion()
/* 180:    */   {
/* 181:207 */     return this.listaIndicadorFinancieroConValorDeExpresion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaIndicadorFinancieroConValorDeVariable(List<IndicadorFinanciero> listaIndicadorFinancieroConValorDeExpresion)
/* 185:    */   {
/* 186:212 */     this.listaIndicadorFinancieroConValorDeExpresion = listaIndicadorFinancieroConValorDeExpresion;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<IndicadorFinanciero> getListaIndicadorFinancieroSeleccionados()
/* 190:    */   {
/* 191:223 */     return this.listaIndicadorFinancieroSeleccionados;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaIndicadorFinancieroSeleccionados(List<IndicadorFinanciero> listaIndicadorFinancieroSeleccionados)
/* 195:    */   {
/* 196:230 */     this.listaIndicadorFinancieroSeleccionados = listaIndicadorFinancieroSeleccionados;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaIndicadorFinancieroConValorDeExpresion(List<IndicadorFinanciero> listaIndicadorFinancieroConValorDeExpresion)
/* 200:    */   {
/* 201:237 */     this.listaIndicadorFinancieroConValorDeExpresion = listaIndicadorFinancieroConValorDeExpresion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getAnnio()
/* 205:    */   {
/* 206:243 */     return this.annio;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setAnnio(int annio)
/* 210:    */   {
/* 211:249 */     this.annio = annio;
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.AnalisisIndicadorFinancieroBean
 * JD-Core Version:    0.7.0.1
 */