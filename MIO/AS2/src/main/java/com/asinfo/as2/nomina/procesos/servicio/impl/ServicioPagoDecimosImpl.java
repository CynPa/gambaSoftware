/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PagoDecimosDao;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.enumeraciones.Genero;
/*   6:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   7:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoDecimos;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioPagoDecimosImpl
/*  17:    */   implements ServicioPagoDecimos
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private transient PagoDecimosDao pagoDecimosDao;
/*  21:    */   @EJB
/*  22:    */   private transient ServicioEmpleado servicioEmpleado;
/*  23:    */   
/*  24:    */   public List<Object[]> datosArchivoDecimoTercero(Date fechaDesde, Date fechaHasta)
/*  25:    */   {
/*  26: 51 */     List<Object[]> listaDatosArchivo = new ArrayList();
/*  27: 52 */     List<Object[]> lista = this.pagoDecimosDao.datosDecimo(fechaDesde, fechaHasta);
/*  28: 54 */     for (Object[] objeto : lista)
/*  29:    */     {
/*  30: 56 */       Object[] datos = new Object[10];
/*  31:    */       
/*  32: 58 */       datos[0] = objeto[0];
/*  33: 59 */       datos[1] = objeto[1];
/*  34: 60 */       datos[2] = objeto[2];
/*  35: 61 */       datos[3] = Character.valueOf(obtenerGenero((Genero)objeto[3]));
/*  36: 62 */       datos[4] = objeto[4];
/*  37: 63 */       datos[5] = objeto[5];
/*  38: 64 */       datos[6] = objeto[6];
/*  39: 65 */       datos[7] = "";
/*  40: 66 */       datos[8] = "";
/*  41: 67 */       datos[9] = "";
/*  42:    */       
/*  43: 69 */       listaDatosArchivo.add(datos);
/*  44:    */     }
/*  45: 72 */     return listaDatosArchivo;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<Object[]> datosArchivoDecimoCuarto(Date fechaDesde, Date fechaHasta)
/*  49:    */   {
/*  50: 81 */     List<Object[]> listaDatosArchivo = new ArrayList();
/*  51: 82 */     List<Object[]> lista = this.pagoDecimosDao.datosDecimo(fechaDesde, fechaHasta);
/*  52: 84 */     for (Object[] objeto : lista)
/*  53:    */     {
/*  54: 86 */       Object[] datos = new Object[11];
/*  55:    */       
/*  56: 88 */       datos[0] = objeto[0];
/*  57: 89 */       datos[1] = objeto[1];
/*  58: 90 */       datos[2] = objeto[2];
/*  59: 91 */       datos[3] = Character.valueOf(obtenerGenero((Genero)objeto[3]));
/*  60: 92 */       datos[4] = "1111111111111";
/*  61: 93 */       datos[6] = objeto[6];
/*  62: 94 */       datos[5] = Character.valueOf('P');
/*  63: 95 */       datos[7] = "";
/*  64: 96 */       datos[8] = "";
/*  65: 97 */       datos[9] = "";
/*  66: 98 */       datos[10] = "";
/*  67:    */       
/*  68:100 */       listaDatosArchivo.add(datos);
/*  69:    */     }
/*  70:103 */     return listaDatosArchivo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Object[]> datosArchivoUtilidades(int anio, String codigoDT, String codigoDC, String codigoFR)
/*  74:    */   {
/*  75:112 */     List<Object[]> listaDatosArchivo = new ArrayList();
/*  76:113 */     System.out.println("**********");
/*  77:114 */     List<Object[]> lista = this.pagoDecimosDao.datosUtilidad(anio, codigoDT, codigoDC, codigoFR);
/*  78:115 */     System.out.println("++++++++++");
/*  79:117 */     for (Object[] objeto : lista)
/*  80:    */     {
/*  81:119 */       Object[] datos = new Object[18];
/*  82:    */       
/*  83:121 */       datos[0] = objeto[0];
/*  84:122 */       datos[1] = objeto[1];
/*  85:123 */       datos[2] = objeto[2];
/*  86:124 */       datos[3] = Character.valueOf(obtenerGenero((Genero)objeto[3]));
/*  87:125 */       datos[4] = objeto[4];
/*  88:126 */       datos[5] = Integer.valueOf(obtenerCargasFamiliares(((Integer)objeto[7]).intValue()));
/*  89:127 */       datos[6] = objeto[6];
/*  90:128 */       datos[7] = Character.valueOf('P');
/*  91:129 */       datos[7] = "";
/*  92:130 */       datos[8] = "";
/*  93:131 */       datos[9] = Character.valueOf(obtenerDiscapacidad(((Integer)objeto[7]).intValue()));
/*  94:132 */       datos[10] = "";
/*  95:133 */       datos[11] = objeto[8];
/*  96:134 */       datos[12] = objeto[9];
/*  97:135 */       datos[13] = "";
/*  98:136 */       datos[14] = objeto[5];
/*  99:137 */       datos[15] = objeto[10];
/* 100:138 */       datos[16] = "";
/* 101:139 */       datos[17] = "";
/* 102:    */       
/* 103:    */ 
/* 104:142 */       listaDatosArchivo.add(datos);
/* 105:    */     }
/* 106:145 */     return listaDatosArchivo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public char obtenerGenero(Genero genero)
/* 110:    */   {
/* 111:156 */     char generoAux = ' ';
/* 112:158 */     if (genero.equals(Genero.MASCULINO)) {
/* 113:159 */       generoAux = 'M';
/* 114:    */     } else {
/* 115:161 */       generoAux = 'F';
/* 116:    */     }
/* 117:164 */     return generoAux;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int obtenerCargasFamiliares(int idEmpleado)
/* 121:    */   {
/* 122:174 */     int cargas = 0;
/* 123:175 */     Empleado empleado = this.servicioEmpleado.buscarPorId(idEmpleado);
/* 124:176 */     cargas = empleado.getListaCargaEmpleado().size();
/* 125:177 */     return cargas;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public char obtenerDiscapacidad(int idEmpleado)
/* 129:    */   {
/* 130:186 */     char discapacidad = ' ';
/* 131:187 */     Empleado empleado = this.servicioEmpleado.buscarPorId(idEmpleado);
/* 132:189 */     if (empleado.getTipoDiscapacidad() != null) {
/* 133:190 */       discapacidad = 'X';
/* 134:    */     }
/* 135:192 */     return discapacidad;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPagoDecimosImpl
 * JD-Core Version:    0.7.0.1
 */