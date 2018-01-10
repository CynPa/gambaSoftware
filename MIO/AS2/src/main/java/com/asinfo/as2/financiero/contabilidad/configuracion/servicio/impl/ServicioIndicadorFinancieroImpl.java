/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleIndicadorFinancieroReporte;
/*   4:    */ import com.asinfo.as2.clases.IndicadorFinancieroReporte;
/*   5:    */ import com.asinfo.as2.dao.IndicadorFinancieroDao;
/*   6:    */ import com.asinfo.as2.entities.IndicadorFinanciero;
/*   7:    */ import com.asinfo.as2.entities.Variable;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioIndicadorFinanciero;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable;
/*  10:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import java.math.BigDecimal;
/*  13:    */ import java.math.RoundingMode;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.HashSet;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Set;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ import net.objecthunter.exp4j.Expression;
/*  24:    */ import net.objecthunter.exp4j.ExpressionBuilder;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class ServicioIndicadorFinancieroImpl
/*  30:    */   implements ServicioIndicadorFinanciero
/*  31:    */ {
/*  32:    */   @EJB
/*  33:    */   private IndicadorFinancieroDao indicadorFinancieroDao;
/*  34:    */   @EJB
/*  35:    */   private ServicioVariable servicioVariable;
/*  36:    */   
/*  37:    */   public void guardar(IndicadorFinanciero indicadorFinanciero)
/*  38:    */     throws ExcepcionAS2Financiero
/*  39:    */   {
/*  40: 54 */     this.indicadorFinancieroDao.guardar(indicadorFinanciero);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void eliminar(IndicadorFinanciero indicadorFinanciero)
/*  44:    */   {
/*  45: 63 */     this.indicadorFinancieroDao.eliminar(indicadorFinanciero);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public IndicadorFinanciero buscarPorId(Integer id)
/*  49:    */   {
/*  50: 70 */     return (IndicadorFinanciero)this.indicadorFinancieroDao.buscarPorId(id);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<IndicadorFinanciero> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55: 78 */     return this.indicadorFinancieroDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<IndicadorFinanciero> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60: 84 */     return this.indicadorFinancieroDao.obtenerListaCombo(sortField, true, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public IndicadorFinanciero cargarDetalle(int idIndicadorFinanciero)
/*  64:    */   {
/*  65: 90 */     return this.indicadorFinancieroDao.cargarDetalle(idIndicadorFinanciero);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int contarPorCriterio(Map<String, String> filters)
/*  69:    */   {
/*  70: 95 */     return this.indicadorFinancieroDao.contarPorCriterio(filters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public IndicadorFinanciero calcularValorIndicadorFinanciero(IndicadorFinanciero indicadorFinanciero, Date fechaDesde, Date fechaHasta)
/*  74:    */   {
/*  75:107 */     String[] formula = indicadorFinanciero.getExpresion().split("[+-/\\(\\)\\*1234567890]");
/*  76:108 */     List<Variable> listaVariablesIndicadoresFinancieros = new ArrayList();
/*  77:109 */     Set<String> set = new HashSet();
/*  78:110 */     Map<String, Double> map = new HashMap();
/*  79:111 */     for (int i = 0; i < formula.length; i++) {
/*  80:112 */       if ((!formula[i].equals(" ")) && (!formula[i].isEmpty()))
/*  81:    */       {
/*  82:113 */         Variable variable = this.servicioVariable.calcularValorVariable(formula[i], fechaDesde, fechaHasta);
/*  83:114 */         set.add(formula[i]);
/*  84:115 */         map.put(formula[i], Double.valueOf(variable.getValor().doubleValue()));
/*  85:116 */         listaVariablesIndicadoresFinancieros.add(variable);
/*  86:    */       }
/*  87:    */     }
/*  88:    */     double resultado;
/*  89:    */     try
/*  90:    */     {
/*  91:124 */       Expression e = new ExpressionBuilder(indicadorFinanciero.getExpresion()).variables(set).build();
/*  92:125 */       e.setVariables(map);
/*  93:126 */       resultado = e.evaluate();
/*  94:    */     }
/*  95:    */     catch (ArithmeticException ae)
/*  96:    */     {
/*  97:    */       double resultado;
/*  98:130 */       resultado = 0.0D;
/*  99:    */     }
/* 100:134 */     if (indicadorFinanciero.isEstado())
/* 101:    */     {
/* 102:135 */       resultado *= 100.0D;
/* 103:136 */       indicadorFinanciero.setValor(new BigDecimal(resultado).setScale(2, RoundingMode.HALF_UP));
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:138 */       indicadorFinanciero.setValor(new BigDecimal(resultado).setScale(2, RoundingMode.HALF_UP));
/* 108:    */     }
/* 109:140 */     indicadorFinanciero.setListaVariablesIndicadoresFinancieros(listaVariablesIndicadoresFinancieros);
/* 110:    */     
/* 111:142 */     return indicadorFinanciero;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public JRDataSource getReporteIndicadorFinanciero(List<IndicadorFinanciero> listaIndicadorFinanciero, int mesDesde, int mesHasta, int anioDesde, int anioHasta)
/* 115:    */   {
/* 116:155 */     JRDataSource ds = null;
/* 117:    */     
/* 118:    */ 
/* 119:    */ 
/* 120:159 */     List<IndicadorFinancieroReporte> lista = new ArrayList();
/* 121:160 */     for (IndicadorFinanciero indicadorFinanciero : listaIndicadorFinanciero)
/* 122:    */     {
/* 123:161 */       IndicadorFinancieroReporte indicadorFinancieroReporte = new IndicadorFinancieroReporte();
/* 124:162 */       indicadorFinancieroReporte.setNombre(indicadorFinanciero.getNombre());
/* 125:163 */       indicadorFinancieroReporte.setListaDetalleIndicadorFinanciero(new ArrayList());
/* 126:    */       Date fechaHasta;
/* 127:    */       IndicadorFinanciero indicadorFinancieroAux;
/* 128:164 */       for (int i = mesDesde; i <= mesHasta; i++)
/* 129:    */       {
/* 130:165 */         Date fechaDesde = FuncionesUtiles.getFecha(1, i, anioDesde);
/* 131:166 */         fechaHasta = FuncionesUtiles.getFechaFinMes(anioHasta, i);
/* 132:167 */         indicadorFinancieroAux = calcularValorIndicadorFinanciero(indicadorFinanciero, fechaDesde, fechaHasta);
/* 133:169 */         for (Variable variable : indicadorFinanciero.getListaVariablesIndicadoresFinancieros())
/* 134:    */         {
/* 135:170 */           DetalleIndicadorFinancieroReporte detalleIndicadorFinancieroReporte = new DetalleIndicadorFinancieroReporte();
/* 136:171 */           detalleIndicadorFinancieroReporte.setF_expresion(indicadorFinancieroAux.getExpresion());
/* 137:172 */           detalleIndicadorFinancieroReporte.setF_mes("(" + FuncionesUtiles.completarALaIzquierda('0', 2, new StringBuilder().append("").append(i).toString()) + ")" + FuncionesUtiles.Mes(fechaHasta.getMonth()) + "/" + (1900 + fechaHasta.getYear()));
/* 138:173 */           String cadenaValorTotal = "";
/* 139:174 */           cadenaValorTotal = cadenaValorTotal + indicadorFinanciero.getValor().multiply(new BigDecimal(-1));
/* 140:180 */           if (indicadorFinanciero.isEstado()) {
/* 141:181 */             cadenaValorTotal = cadenaValorTotal + "%";
/* 142:    */           }
/* 143:183 */           detalleIndicadorFinancieroReporte.setF_valorTotal(cadenaValorTotal);
/* 144:184 */           detalleIndicadorFinancieroReporte.setF_nombreVariable(variable.getNombre());
/* 145:185 */           detalleIndicadorFinancieroReporte.setF_valorVariable(variable.getValor());
/* 146:186 */           indicadorFinancieroReporte.getListaDetalleIndicadorFinanciero().add(detalleIndicadorFinancieroReporte);
/* 147:    */         }
/* 148:    */       }
/* 149:191 */       lista.add(indicadorFinancieroReporte);
/* 150:    */     }
/* 151:193 */     ds = new JRBeanCollectionDataSource(lista);
/* 152:194 */     return ds;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioIndicadorFinancieroImpl
 * JD-Core Version:    0.7.0.1
 */