/*   1:    */ package com.asinfo.as2.produccion.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.entities.Dispositivo;
/*   5:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   6:    */ import com.asinfo.as2.entities.MarcacionDispositivo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Unidad;
/*  10:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  11:    */ import com.asinfo.as2.entities.UnidadManejoProducto;
/*  12:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*  16:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivoRemote;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.math.RoundingMode;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.Stateless;
/*  22:    */ import javax.persistence.EntityManager;
/*  23:    */ import javax.persistence.NoResultException;
/*  24:    */ import javax.persistence.PersistenceContext;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ServicioMarcacionDispositivoImpl
/*  29:    */   implements ServicioMarcacionDispositivo, ServicioMarcacionDispositivoRemote
/*  30:    */ {
/*  31:    */   @PersistenceContext(name="AS2PU")
/*  32:    */   public EntityManager em;
/*  33:    */   @EJB
/*  34:    */   private GenericoDao<MarcacionDispositivo> marcacionDispositivoDao;
/*  35:    */   @EJB
/*  36:    */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  37:    */   @EJB
/*  38:    */   private ServicioProducto servicioProducto;
/*  39:    */   
/*  40:    */   public void nuevaMarcacion(String ip, String dispositivo, BigDecimal marcacion)
/*  41:    */   {
/*  42: 47 */     MarcacionDispositivo marcacionDispositivo = new MarcacionDispositivo(ip, dispositivo, marcacion);
/*  43: 48 */     this.marcacionDispositivoDao.guardar(marcacionDispositivo);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void calcularPesoNeto(int idOrganizacion, LecturaBalanza lb, boolean validar)
/*  47:    */     throws AS2Exception
/*  48:    */   {
/*  49: 53 */     BigDecimal numeroUnidadesManejo = new BigDecimal(lb.getNumeroUnidadesManejo() == 0 ? 1 : lb.getNumeroUnidadesManejo());
/*  50: 54 */     lb.setPesoNeto(BigDecimal.ZERO);
/*  51: 55 */     lb.setUnidadManejoProducto(null);
/*  52: 57 */     if ((!lb.getDispositivo().isFueraLinea()) || (lb.getPesoBruto().compareTo(BigDecimal.ZERO) == 0)) {
/*  53: 58 */       lb.setPesoBruto(this.servicioMarcacionDispositivo.getMarcacion(AppUtil.getOrganizacion().getId(), lb.getDispositivo().getIp()));
/*  54:    */     }
/*  55: 63 */     BigDecimal pesoNeto = lb.getPesoBruto();
/*  56: 64 */     if (lb.getPallet() != null) {
/*  57: 65 */       pesoNeto = pesoNeto.subtract(lb.getPallet().getPeso());
/*  58:    */     }
/*  59: 67 */     if (!lb.getProducto().isIndicadorPesoBalanza())
/*  60:    */     {
/*  61: 68 */       lb.setPesoNeto(pesoNeto);
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 71 */       pesoNeto = pesoNeto.subtract(lb.getUnidadManejo().getPeso().multiply(numeroUnidadesManejo));
/*  66: 72 */       lb.setPesoNeto(pesoNeto);
/*  67: 73 */       lb.setUnidadManejoProducto(this.servicioProducto.getUnidadManejoProducto(lb.getProducto(), lb.getUnidadManejo()));
/*  68: 74 */       if ((lb.getUnidadManejoProducto() == null) && (validar))
/*  69:    */       {
/*  70: 75 */         lb.setPesoNeto(BigDecimal.ZERO);
/*  71:    */         
/*  72: 77 */         throw new AS2Exception("com.asinfo.as2.clases.LecturaBalanza.ERROR_PRODUCTO_PESO_POR_UNIDAD_MANEJO_NO_DEFINIDO", new String[] { lb.getProducto().getCodigo(), lb.getProducto().getNombre() });
/*  73:    */       }
/*  74: 81 */       if ((validar) && (lb.getUnidadManejoProducto().getCantidad() != null) && (lb.getUnidadManejoProducto().getCantidad().intValue() != 0) && 
/*  75: 82 */         (!lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad())) {
/*  76: 83 */         lb.setCantidad(Integer.valueOf(numeroUnidadesManejo.multiply(new BigDecimal(lb.getUnidadManejoProducto().getCantidad().intValue())).intValue()));
/*  77:    */       }
/*  78: 88 */       if (validar) {
/*  79: 89 */         validarCantidad(lb);
/*  80:    */       }
/*  81:    */     }
/*  82: 93 */     if (lb.getPesoNeto().compareTo(BigDecimal.ZERO) <= 0)
/*  83:    */     {
/*  84: 94 */       lb.setPesoNeto(BigDecimal.ZERO);
/*  85:    */       
/*  86: 96 */       throw new AS2Exception("com.asinfo.as2.clases.LecturaBalanza.ERROR_PESO_DISPOSITIVO_CERO", new String[] { lb.getProducto().getCodigo(), lb.getProducto().getNombre(), lb.getPesoNeto().toString() });
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void calcularCantidad(LecturaBalanza lb)
/*  91:    */   {
/*  92:102 */     if ((lb != null) && (lb.getUnidadManejo() != null) && (lb.getProducto() != null))
/*  93:    */     {
/*  94:103 */       BigDecimal numeroUnidadesManejo = new BigDecimal(lb.getNumeroUnidadesManejo() == 0 ? 1 : lb.getNumeroUnidadesManejo());
/*  95:104 */       lb.setUnidadManejoProducto(this.servicioProducto.getUnidadManejoProducto(lb.getProducto(), lb.getUnidadManejo()));
/*  96:105 */       if ((lb.getUnidadManejoProducto().getCantidad() != null) && (
/*  97:106 */         (lb.getUnidadManejoProducto().getCantidad().intValue() != 0) || 
/*  98:107 */         (lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad()))) {
/*  99:108 */         lb.setCantidad(Integer.valueOf(numeroUnidadesManejo.multiply(new BigDecimal(lb.getUnidadManejoProducto().getCantidad().intValue())).intValue()));
/* 100:109 */       } else if ((lb.getUnidadManejoProducto().getCantidad() == null) && 
/* 101:110 */         (lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad())) {
/* 102:111 */         lb.setCantidad(Integer.valueOf(0));
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void validarCantidad(LecturaBalanza lb)
/* 108:    */     throws AS2Exception
/* 109:    */   {
/* 110:118 */     if ((lb.getUnidadManejoProducto().getCantidad() == null) && (lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad())) {
/* 111:119 */       lb.setCantidad(Integer.valueOf(0));
/* 112:    */     }
/* 113:122 */     if ((lb.getProducto() != null) && (lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad()) && (lb.getCantidad().intValue() > 0) && 
/* 114:123 */       (lb.getPesoNeto() != null) && (lb.getPesoNeto().compareTo(BigDecimal.ZERO) > 0))
/* 115:    */     {
/* 116:124 */       BigDecimal pesoUnitarioNeto = lb.getPesoNeto().divide(new BigDecimal(lb.getCantidad().intValue()), 2, RoundingMode.HALF_UP);
/* 117:126 */       if ((pesoUnitarioNeto.compareTo(lb.getProducto().getPesoMinimo()) < 0) || (pesoUnitarioNeto.compareTo(lb.getProducto().getPesoMaximo()) > 0)) {
/* 118:128 */         throw new AS2Exception("com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioMarcacionDispositivo.ERROR_PESO_UNITARIO_FUERA_RANGO", new String[] { pesoUnitarioNeto.toString(), lb.getProducto().getNombre(), lb.getProducto().getPesoMinimo().toString(), lb.getProducto().getPesoMaximo().toString() });
/* 119:    */       }
/* 120:    */     }
/* 121:130 */     else if ((lb.getProducto().isIndicadorPesoBalanza()) && (!lb.getUnidadManejoProducto().getUnidadManejo().isIndicadorDigitarCantidad()))
/* 122:    */     {
/* 123:131 */       BigDecimal numeroUnidadesManejo = new BigDecimal(lb.getNumeroUnidadesManejo() == 0 ? 1 : lb.getNumeroUnidadesManejo());
/* 124:132 */       BigDecimal pesoParlet = BigDecimal.ZERO;
/* 125:133 */       if (lb.getPallet() != null) {
/* 126:134 */         pesoParlet = lb.getPallet().getPeso();
/* 127:    */       }
/* 128:137 */       BigDecimal pesoMinimo = lb.getUnidadManejoProducto().getPesoMinimo().multiply(numeroUnidadesManejo).add(pesoParlet);
/* 129:138 */       BigDecimal pesoMaximo = lb.getUnidadManejoProducto().getPesoMaximo().multiply(numeroUnidadesManejo).add(pesoParlet);
/* 130:140 */       if ((lb.getPesoBruto().compareTo(BigDecimal.ZERO) <= 0) || (lb.getPesoBruto().compareTo(pesoMinimo) < 0) || 
/* 131:141 */         (lb.getPesoBruto().compareTo(pesoMaximo) > 0))
/* 132:    */       {
/* 133:143 */         lb.setPesoNeto(BigDecimal.ZERO);
/* 134:    */         
/* 135:145 */         throw new AS2Exception("com.asinfo.as2.clases.LecturaBalanza.ERROR_PESO_POR_UNIDAD_MANEJO_ERRONEO", new String[] { lb.getProducto().getCodigo(), lb.getProducto().getNombre(), lb.getPesoBruto().toString(), pesoMinimo.toString(), pesoMaximo.toString() });
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal getMarcacion(int idOrganizacion, String ip)
/* 141:    */   {
/* 142:153 */     StringBuilder sqll = new StringBuilder();
/* 143:154 */     sqll.append(" SELECT md.marcacion FROM MarcacionDispositivo md");
/* 144:155 */     sqll.append(" WHERE md.ip = :ip");
/* 145:156 */     sqll.append(" ORDER BY md.fechaCreacion DESC");
/* 146:157 */     Query query = this.em.createQuery(sqll.toString());
/* 147:158 */     query.setParameter("ip", ip);
/* 148:159 */     query.setMaxResults(1);
/* 149:    */     try
/* 150:    */     {
/* 151:162 */       return (BigDecimal)query.getSingleResult();
/* 152:    */     }
/* 153:    */     catch (NoResultException ex) {}
/* 154:164 */     return BigDecimal.ZERO;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BigDecimal[] getCantidades(LecturaBalanza lecturaBalanza)
/* 158:    */     throws AS2Exception
/* 159:    */   {
/* 160:170 */     BigDecimal cantidad = null;
/* 161:171 */     BigDecimal cantidadInformativa = null;
/* 162:172 */     Producto producto = lecturaBalanza.getProducto();
/* 163:174 */     if (producto.isIndicadorManejaUnidadInformativa())
/* 164:    */     {
/* 165:175 */       if ((lecturaBalanza.getCantidad() == null) || (lecturaBalanza.getCantidad().intValue() == 0)) {
/* 166:176 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_FALTA_PARAMETRIZAR_CANTIDAD_GAVETA_UNIDAD_INFORMATIVA", new String[] { producto.getNombre() });
/* 167:    */       }
/* 168:180 */       if ((producto.getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)) && 
/* 169:181 */         (!producto.getUnidadInformativa().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)))
/* 170:    */       {
/* 171:182 */         cantidad = lecturaBalanza.getPesoNeto();
/* 172:183 */         cantidadInformativa = new BigDecimal(lecturaBalanza.getCantidad().intValue());
/* 173:    */       }
/* 174:184 */       else if ((!producto.getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)) && 
/* 175:185 */         (producto.getUnidadInformativa().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)))
/* 176:    */       {
/* 177:186 */         cantidadInformativa = lecturaBalanza.getPesoNeto();
/* 178:187 */         cantidad = new BigDecimal(lecturaBalanza.getCantidad().intValue());
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:189 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_PRODUCTO_UNIDAD_INFORMATIVA_BALANZA_MAL_TIPO_UNIDADES", new String[] { producto.getNombre() });
/* 183:    */       }
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:192 */       cantidad = lecturaBalanza.getPesoNeto();
/* 188:193 */       if ((lecturaBalanza.getCantidad() != null) && (lecturaBalanza.getCantidad().intValue() != 0)) {
/* 189:194 */         cantidad = new BigDecimal(lecturaBalanza.getCantidad().intValue());
/* 190:    */       }
/* 191:    */     }
/* 192:197 */     BigDecimal[] cantidades = new BigDecimal[2];
/* 193:198 */     cantidades[0] = cantidad;
/* 194:199 */     cantidades[1] = cantidadInformativa;
/* 195:200 */     return cantidades;
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioMarcacionDispositivoImpl
 * JD-Core Version:    0.7.0.1
 */