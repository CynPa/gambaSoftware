/*   1:    */ package com.asinfo.as2.caja.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.reportes.servicio.ServicioReporteCaja;
/*   4:    */ import com.asinfo.as2.dao.CajaDao;
/*   5:    */ import com.asinfo.as2.dao.reportes.caja.ReporteCajaDao;
/*   6:    */ import com.asinfo.as2.entities.CierreCaja;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.text.SimpleDateFormat;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.TreeMap;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ServicioReporteCajaImpl
/*  21:    */   implements ServicioReporteCaja
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private CajaDao cajaDao;
/*  25:    */   @EJB
/*  26:    */   private ReporteCajaDao reporteCajaDao;
/*  27:    */   
/*  28:    */   public List obtenerReporteCaja(Date fechaDesde, Date fechaHasta, int idUsuario, Estado estadoCaja)
/*  29:    */   {
/*  30: 54 */     return this.cajaDao.obtenerReporteCaja(fechaDesde, fechaHasta, idUsuario, estadoCaja);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List getReporteCierreCaja(int idCierreCaja)
/*  34:    */   {
/*  35: 65 */     return this.reporteCajaDao.getReporteCierreCaja(idCierreCaja);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<Object[]> getReporteCierreCajaPorDenominacionFormaCobro(int idCierreCaja)
/*  39:    */   {
/*  40: 70 */     return this.reporteCajaDao.getReporteCierreCajaPorDenominacionFormaCobro(idCierreCaja);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<Object[]> getListaCierreCaja(CierreCaja cierreCaja)
/*  44:    */   {
/*  45: 75 */     List<Object[]> listaTotal = new ArrayList();
/*  46:    */     
/*  47: 77 */     List<Object[]> lista2 = this.reporteCajaDao.getListaFactura(cierreCaja);
/*  48:    */     
/*  49: 79 */     List<Object[]> lista1 = this.reporteCajaDao.getReporteCierreCajaPorFactura(cierreCaja);
/*  50: 80 */     List<Object[]> lista4 = this.reporteCajaDao.getListaAnticipoCliente(cierreCaja);
/*  51:    */     
/*  52: 82 */     listaTotal.addAll(lista1);
/*  53: 83 */     listaTotal.addAll(lista2);
/*  54: 84 */     listaTotal.addAll(lista4);
/*  55:    */     
/*  56:    */ 
/*  57: 87 */     TreeMap<String, Object[]> tmReporte = new TreeMap();
/*  58: 88 */     for (Object[] objects : listaTotal)
/*  59:    */     {
/*  60: 89 */       String clave = (String)objects[15] + "-" + new SimpleDateFormat("yyyyMMdd").format((Date)objects[5]) + "-" + (String)objects[14] + "-" + (String)objects[6] + objects.hashCode();
/*  61: 90 */       tmReporte.put(clave, objects);
/*  62:    */     }
/*  63: 93 */     return new ArrayList(tmReporte.values());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public BigDecimal totalChquesPosfechados(CierreCaja cierreCaja)
/*  67:    */   {
/*  68: 99 */     return this.reporteCajaDao.totalChquesPosfechados(cierreCaja);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Integer numeroChequesPosfechados(CierreCaja cierreCaja)
/*  72:    */   {
/*  73:105 */     Integer a = this.reporteCajaDao.numeroChequesPosfechados(cierreCaja);
/*  74:106 */     return a;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<Object[]> getReporteCierreCajaComparativo(int idCierreCaja)
/*  78:    */   {
/*  79:112 */     List<Object[]> list = this.reporteCajaDao.getReporteCierreCaja(idCierreCaja);
/*  80:113 */     Map<String, Object[]> hashCierreCaja = new HashMap();
/*  81:114 */     for (Object[] objects : list)
/*  82:    */     {
/*  83:115 */       obj = (Object[])hashCierreCaja.get((String)objects[1] + "~" + (String)objects[6]);
/*  84:116 */       if (obj != null)
/*  85:    */       {
/*  86:117 */         obj[6] = ((BigDecimal)obj[6]).add((BigDecimal)objects[15]);
/*  87:118 */         hashCierreCaja.put((String)objects[1] + "~" + (String)objects[6], obj);
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:120 */         obj = new Object[9];
/*  92:121 */         obj[0] = objects[0];
/*  93:122 */         obj[1] = objects[1];
/*  94:123 */         obj[2] = objects[2];
/*  95:124 */         obj[3] = objects[3];
/*  96:125 */         obj[4] = objects[10];
/*  97:126 */         obj[5] = objects[6];
/*  98:127 */         obj[6] = objects[15];
/*  99:128 */         obj[7] = "AS2";
/* 100:129 */         obj[8] = BigDecimal.ZERO;
/* 101:130 */         hashCierreCaja.put((String)objects[1] + "~" + (String)objects[6], obj);
/* 102:    */       }
/* 103:    */     }
/* 104:    */     Object[] obj;
/* 105:134 */     Object listResult = this.reporteCajaDao.getReporteCierreCajaComparativo(idCierreCaja);
/* 106:135 */     ((List)listResult).addAll(hashCierreCaja.values());
/* 107:136 */     Map<String, BigDecimal> hashObject = new HashMap();
/* 108:137 */     for (Object[] objects : (List)listResult)
/* 109:    */     {
/* 110:138 */       bd = (BigDecimal)hashObject.get((String)objects[7]);
/* 111:139 */       if (bd != null)
/* 112:    */       {
/* 113:140 */         bd = bd.add((BigDecimal)objects[6]);
/* 114:141 */         hashObject.put((String)objects[7], bd);
/* 115:    */       }
/* 116:    */       else
/* 117:    */       {
/* 118:143 */         hashObject.put((String)objects[7], (BigDecimal)objects[6]);
/* 119:    */       }
/* 120:    */     }
/* 121:    */     BigDecimal bd;
/* 122:146 */     List<BigDecimal> listBD = new ArrayList();
/* 123:147 */     listBD.addAll(hashObject.values());
/* 124:    */     BigDecimal result;
/* 125:148 */     if (listBD.size() == 2)
/* 126:    */     {
/* 127:149 */       result = ((BigDecimal)listBD.get(0)).subtract((BigDecimal)listBD.get(1));
/* 128:150 */       for (Object[] objects : (List)listResult) {
/* 129:151 */         objects[8] = result;
/* 130:    */       }
/* 131:    */     }
/* 132:155 */     return listResult;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.servicio.impl.ServicioReporteCajaImpl
 * JD-Core Version:    0.7.0.1
 */