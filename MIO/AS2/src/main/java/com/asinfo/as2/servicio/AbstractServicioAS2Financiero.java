/*   1:    */ package com.asinfo.as2.servicio;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   9:    */ import com.asinfo.as2.entities.DimensionContable;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.FormaPago;
/*  12:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.TransactionManagement;
/*  24:    */ import javax.ejb.TransactionManagementType;
/*  25:    */ 
/*  26:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  27:    */ public abstract class AbstractServicioAS2Financiero
/*  28:    */   extends AbstractServicioAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 5498708984204072799L;
/*  31:    */   @EJB
/*  32:    */   protected ServicioCuentaContable servicioCuentaContable;
/*  33:    */   @EJB
/*  34:    */   protected ServicioAsiento servicioAsiento;
/*  35:    */   @EJB
/*  36:    */   protected ServicioFormaPago servicioFormaPago;
/*  37:    */   @EJB
/*  38:    */   protected ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  39:    */   
/*  40:    */   protected void generarAsiento(Asiento asiento, List<DetalleInterfazContable> listaDAIC, Documento documentoOrigen)
/*  41:    */     throws ExcepcionAS2Financiero
/*  42:    */   {
/*  43: 70 */     generarAsiento(asiento, listaDAIC, documentoOrigen, 0);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void generarAsiento(Asiento asiento, List<DetalleInterfazContable> listaDAIC, Documento documentoOrigen, int idMovimientoBancario)
/*  47:    */     throws ExcepcionAS2Financiero
/*  48:    */   {
/*  49: 86 */     boolean auditar = false;
/*  50: 88 */     if (asiento.getId() == 0) {
/*  51: 89 */       asiento.setEstado(Estado.ELABORADO);
/*  52:    */     }
/*  53: 93 */     asiento.setIndicadorAutomatico(true);
/*  54:    */     
/*  55:    */ 
/*  56: 96 */     StringBuilder sbConcepto = new StringBuilder();
/*  57: 97 */     sbConcepto.append(asiento.getConcepto() + " ");
/*  58:    */     try
/*  59:    */     {
/*  60: 99 */       for (DetalleInterfazContable d : listaDAIC) {
/*  61:100 */         if (!sbConcepto.toString().contains(d.getReferencia1())) {
/*  62:101 */           sbConcepto.append(d.getReferencia1().trim()).append(" ");
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68:105 */       e.printStackTrace();
/*  69:    */     }
/*  70:108 */     asiento.setConcepto(sbConcepto.toString());
/*  71:110 */     if (asiento.getConcepto().length() > 1000) {
/*  72:111 */       asiento.setConcepto(asiento.getConcepto().substring(0, 999));
/*  73:    */     }
/*  74:113 */     if (documentoOrigen != null) {
/*  75:114 */       asiento.setDocumentoOrigen(documentoOrigen);
/*  76:    */     }
/*  77:117 */     int numeroLineas = Math.max(asiento.getListaDetalleAsiento().size(), listaDAIC.size());
/*  78:123 */     for (int i = 0; i < numeroLineas; i++) {
/*  79:124 */       if (i >= listaDAIC.size())
/*  80:    */       {
/*  81:125 */         DetalleAsiento detalleAsiento = (DetalleAsiento)asiento.getListaDetalleAsiento().get(i);
/*  82:    */         
/*  83:127 */         detalleAsiento.setEliminado(true);
/*  84:128 */         if (detalleAsiento.getMovimientoBancario() != null) {
/*  85:129 */           detalleAsiento.getMovimientoBancario().setEliminado(true);
/*  86:    */         }
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:132 */         DetalleInterfazContable detalleInterfazContable = (DetalleInterfazContable)listaDAIC.get(i);
/*  91:133 */         DetalleAsiento detalleAsiento = null;
/*  92:135 */         if (i < asiento.getListaDetalleAsiento().size())
/*  93:    */         {
/*  94:136 */           detalleAsiento = (DetalleAsiento)asiento.getListaDetalleAsiento().get(i);
/*  95:    */         }
/*  96:    */         else
/*  97:    */         {
/*  98:138 */           detalleAsiento = new DetalleAsiento();
/*  99:139 */           detalleAsiento.setAsiento(asiento);
/* 100:    */         }
/* 101:141 */         detalleAsiento.setEliminado(false);
/* 102:142 */         detalleAsiento.setIdOrganizacion(asiento.getIdOrganizacion());
/* 103:143 */         detalleAsiento.setIdSucursal(asiento.getSucursal().getId());
/* 104:146 */         if (detalleInterfazContable.getIdDimensionContable1() != null) {
/* 105:147 */           detalleAsiento.setDimensionContable1(new DimensionContable(detalleInterfazContable.getIdDimensionContable1().intValue()));
/* 106:    */         } else {
/* 107:149 */           detalleAsiento.setDimensionContable1(null);
/* 108:    */         }
/* 109:151 */         if (detalleInterfazContable.getIdDimensionContable2() != null) {
/* 110:152 */           detalleAsiento.setDimensionContable2(new DimensionContable(detalleInterfazContable.getIdDimensionContable2().intValue()));
/* 111:    */         } else {
/* 112:154 */           detalleAsiento.setDimensionContable2(null);
/* 113:    */         }
/* 114:156 */         if (detalleInterfazContable.getIdDimensionContable3() != null) {
/* 115:157 */           detalleAsiento.setDimensionContable3(new DimensionContable(detalleInterfazContable.getIdDimensionContable3().intValue()));
/* 116:    */         } else {
/* 117:159 */           detalleAsiento.setDimensionContable3(null);
/* 118:    */         }
/* 119:161 */         if (detalleInterfazContable.getIdDimensionContable4() != null) {
/* 120:162 */           detalleAsiento.setDimensionContable4(new DimensionContable(detalleInterfazContable.getIdDimensionContable4().intValue()));
/* 121:    */         } else {
/* 122:164 */           detalleAsiento.setDimensionContable4(null);
/* 123:    */         }
/* 124:166 */         if (detalleInterfazContable.getIdDimensionContable5() != null) {
/* 125:167 */           detalleAsiento.setDimensionContable5(new DimensionContable(detalleInterfazContable.getIdDimensionContable5().intValue()));
/* 126:    */         } else {
/* 127:169 */           detalleAsiento.setDimensionContable5(null);
/* 128:    */         }
/* 129:172 */         BigDecimal debe = BigDecimal.ZERO;
/* 130:173 */         BigDecimal haber = BigDecimal.ZERO;
/* 131:175 */         if (detalleInterfazContable.getValor().compareTo(BigDecimal.ZERO) > 0) {
/* 132:176 */           debe = detalleInterfazContable.getValor().setScale(2);
/* 133:    */         } else {
/* 134:178 */           haber = detalleInterfazContable.getValor().negate().setScale(2);
/* 135:    */         }
/* 136:180 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorId(Integer.valueOf(detalleInterfazContable.getIdCuentaContable()));
/* 137:    */         
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:185 */         detalleAsiento.setCuentaContable(cuentaContable);
/* 142:    */         
/* 143:187 */         detalleAsiento.setDebe(debe);
/* 144:188 */         detalleAsiento.setHaber(haber);
/* 145:189 */         String descripcion = "";
/* 146:190 */         if (detalleInterfazContable.getReferencia2() != null) {
/* 147:191 */           descripcion = detalleInterfazContable.getReferencia2().trim();
/* 148:    */         }
/* 149:193 */         if ((detalleInterfazContable.getReferencia3() != null) && (!detalleInterfazContable.getReferencia3().isEmpty())) {
/* 150:194 */           descripcion = descripcion + "-" + detalleInterfazContable.getReferencia3();
/* 151:    */         }
/* 152:197 */         if ((detalleInterfazContable.getReferencia1() != null) && (!detalleInterfazContable.getReferencia1().isEmpty())) {
/* 153:198 */           descripcion = descripcion + "-" + detalleInterfazContable.getReferencia1();
/* 154:    */         }
/* 155:201 */         if (descripcion.length() > 200) {
/* 156:202 */           descripcion = descripcion.substring(0, 200);
/* 157:    */         }
/* 158:204 */         detalleAsiento.setDescripcion(descripcion);
/* 159:206 */         if ((detalleAsiento.getCuentaContable().getTipoCuentaContable() == TipoCuentaContable.BANCO) && 
/* 160:207 */           (detalleInterfazContable.getIdFormaPago() != null))
/* 161:    */         {
/* 162:210 */           CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.buscarPorCuentaContable(detalleAsiento
/* 163:211 */             .getCuentaContable().getId());
/* 164:212 */           if ((idMovimientoBancario <= 0) && (cuentaBancariaOrganizacion != null))
/* 165:    */           {
/* 166:    */             MovimientoBancario movimientoBancario;
/* 167:    */             MovimientoBancario movimientoBancario;
/* 168:213 */             if (detalleAsiento.getMovimientoBancario() != null)
/* 169:    */             {
/* 170:214 */               movimientoBancario = detalleAsiento.getMovimientoBancario();
/* 171:    */             }
/* 172:    */             else
/* 173:    */             {
/* 174:216 */               movimientoBancario = new MovimientoBancario();
/* 175:217 */               movimientoBancario.setEstado(Estado.CONTABILIZADO);
/* 176:218 */               movimientoBancario.setDetalleAsiento(detalleAsiento);
/* 177:219 */               detalleAsiento.setMovimientoBancario(movimientoBancario);
/* 178:    */             }
/* 179:221 */             movimientoBancario.setIdOrganizacion(asiento.getIdOrganizacion());
/* 180:222 */             movimientoBancario.setIdSucursal(asiento.getSucursal().getId());
/* 181:223 */             movimientoBancario.setBeneficiario("");
/* 182:224 */             if (!detalleInterfazContable.getReferencia4().isEmpty()) {
/* 183:225 */               movimientoBancario.setBeneficiario(detalleInterfazContable.getReferencia4());
/* 184:    */             }
/* 185:227 */             BigDecimal valor = detalleAsiento.getDebe().subtract(detalleAsiento.getHaber());
/* 186:228 */             movimientoBancario.setValor(valor);
/* 187:    */             
/* 188:230 */             movimientoBancario.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 189:    */             
/* 190:232 */             movimientoBancario.setDocumento(documentoOrigen);
/* 191:233 */             if (asiento.getConcepto().length() >= 1000) {
/* 192:234 */               movimientoBancario.setDescripcion(asiento.getConcepto().substring(1000));
/* 193:    */             } else {
/* 194:236 */               movimientoBancario.setDescripcion(asiento.getConcepto());
/* 195:    */             }
/* 196:239 */             movimientoBancario.setDocumentoReferencia(detalleInterfazContable.getReferencia3());
/* 197:240 */             movimientoBancario.setFecha(asiento.getFecha());
/* 198:242 */             if (detalleInterfazContable.getIdFormaPago() != null)
/* 199:    */             {
/* 200:243 */               FormaPago formaPago = this.servicioFormaPago.buscarPorId(detalleInterfazContable.getIdFormaPago());
/* 201:244 */               movimientoBancario.setFormaPago(formaPago);
/* 202:    */             }
/* 203:    */           }
/* 204:    */         }
/* 205:247 */         else if (detalleAsiento.getMovimientoBancario() != null)
/* 206:    */         {
/* 207:248 */           detalleAsiento.getMovimientoBancario().setEliminado(true);
/* 208:    */         }
/* 209:250 */         if (i >= asiento.getListaDetalleAsiento().size()) {
/* 210:251 */           asiento.getListaDetalleAsiento().add(detalleAsiento);
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:258 */     if (!auditar)
/* 215:    */     {
/* 216:259 */       asiento.setAuditable(auditar);
/* 217:260 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento())
/* 218:    */       {
/* 219:261 */         detalleAsiento.setAuditable(auditar);
/* 220:263 */         if (detalleAsiento.getMovimientoBancario() != null) {
/* 221:264 */           detalleAsiento.setAuditable(auditar);
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.AbstractServicioAS2Financiero
 * JD-Core Version:    0.7.0.1
 */