/*   1:    */ package com.asinfo.as2.dao.reportes.ventas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.DetalleHojaRuta;
/*   6:    */ import com.asinfo.as2.entities.GuiaRemision;
/*   7:    */ import com.asinfo.as2.entities.HojaRuta;
/*   8:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReporteGuiaRemisionDao
/*  18:    */   extends AbstractDaoAS2<GuiaRemision>
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private ServicioHojaRuta servicioHojaRuta;
/*  22:    */   
/*  23:    */   public ReporteGuiaRemisionDao()
/*  24:    */   {
/*  25: 23 */     super(GuiaRemision.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List getReporteGuiaRemision(int idDespachoCliente, int idTransferenciaBodega, int idGuiaRemision, HojaRuta hojaRutaTransportista)
/*  29:    */   {
/*  30: 28 */     StringBuffer sql = new StringBuffer();
/*  31: 29 */     if (idDespachoCliente != 0)
/*  32:    */     {
/*  33: 31 */       sql.append("SELECT dc.descripcion, dc.numero, em.identificacion, em.nombreFiscal, co.nombre, cd.nombre, ");
/*  34: 32 */       sql.append(" gr.numero, gr.fecha, gr.tarifa, gr.conductor, gr.licencia, gr.descripcion, ");
/*  35: 33 */       sql.append(" gr.placa, t.nombre, t.identificacion, ");
/*  36: 34 */       sql.append(" p.codigo, p.nombre, p.peso, p.volumen, u.nombre, ");
/*  37: 35 */       sql.append(" ddc.cantidad, ddc.descripcion, fc.numero, p.codigoBarras, ");
/*  38: 36 */       sql.append(" CONCAT(ub.direccion1,' ', ub.direccion2,' ',ub.direccion3,' ',ub.direccion4,' ',ub.direccion5), su.nombre , z.nombre, em.nombreComercial, gr.fechaVigencia, ");
/*  39: 37 */       sql.append(" dc.fecha, dc.numero  ");
/*  40:    */       
/*  41:    */ 
/*  42: 40 */       sql.append(" , o.razonSocial, o.identificacion, gr.fechaAutorizacion, gr.claveAcceso, oc.numeroResolucionContribuyente,");
/*  43: 41 */       sql.append(" oc.indicadorObligadoContabilidad, gr.direccionMatriz, gr.direccionSucursal, gr.ambiente, gr.tipoEmision, gr.email, gr.autorizacion, fcSRI.autorizacion");
/*  44: 42 */       sql.append(" , l.codigo,'','','','', '', '', '', gr.horaSalida, gr.horaLlegada, de.telefono1, d.nombre, pc.descripcion2 ");
/*  45:    */       
/*  46: 44 */       sql.append(" FROM DetalleDespachoCliente ddc ");
/*  47: 45 */       sql.append(" LEFT JOIN ddc.inventarioProducto ip ");
/*  48: 46 */       sql.append(" LEFT JOIN ip.lote l ");
/*  49: 47 */       sql.append(" LEFT JOIN ddc.despachoCliente dc ");
/*  50: 48 */       sql.append(" LEFT JOIN dc.direccionEmpresa de ");
/*  51: 49 */       sql.append(" LEFT JOIN de.ubicacion ub ");
/*  52: 50 */       sql.append(" LEFT JOIN dc.empresa em ");
/*  53: 51 */       sql.append(" LEFT JOIN dc.sucursal su ");
/*  54: 52 */       sql.append(" LEFT JOIN dc.documento d ");
/*  55: 53 */       sql.append(" LEFT JOIN dc.pedidoCliente pc ");
/*  56: 54 */       sql.append(" LEFT JOIN dc.guiaRemision gr, Organizacion o ");
/*  57: 55 */       sql.append(" LEFT JOIN gr.ciudadOrigen co ");
/*  58: 56 */       sql.append(" LEFT JOIN gr.ciudadDestino cd ");
/*  59: 57 */       sql.append(" LEFT JOIN gr.vehiculo v ");
/*  60: 58 */       sql.append(" LEFT JOIN v.transportista t ");
/*  61: 59 */       sql.append(" LEFT JOIN ddc.producto p ");
/*  62: 60 */       sql.append(" LEFT JOIN p.unidad u ");
/*  63: 61 */       sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/*  64: 62 */       sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/*  65: 63 */       sql.append(" LEFT JOIN fc.facturaClienteSRI fcSRI");
/*  66: 64 */       sql.append(" LEFT JOIN fc.zona z ");
/*  67: 65 */       sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/*  68: 66 */       sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/*  69: 67 */       sql.append(" JOIN o.organizacionConfiguracion oc");
/*  70: 68 */       sql.append(" WHERE o.idOrganizacion = gr.idOrganizacion AND dc.idDespachoCliente = :idDespachoCliente ");
/*  71:    */     }
/*  72: 70 */     if (idTransferenciaBodega != 0)
/*  73:    */     {
/*  74: 71 */       sql = new StringBuffer();
/*  75: 72 */       sql.append("SELECT mi.descripcion, mi.numero, org.identificacion, org.razonSocial, co.nombre, cd.nombre, ");
/*  76: 73 */       sql.append(" gr.numero, gr.fecha, gr.tarifa, gr.conductor, gr.licencia, gr.descripcion, ");
/*  77: 74 */       sql.append(" gr.placa, t.nombre, t.identificacion, ");
/*  78: 75 */       sql.append(" p.codigo, p.nombre, p.peso, p.volumen, u.nombre, ");
/*  79: 76 */       sql.append(" dmi.cantidad, dmi.descripcion, '-', p.codigoBarras, ");
/*  80: 77 */       sql.append(" CONCAT(ub.direccion1,' ', ub.direccion2,' ',ub.direccion3,' ',ub.direccion4,' ',ub.direccion5), su.nombre , '-', org.razonSocial, gr.fechaVigencia, ");
/*  81: 78 */       sql.append(" mi.fecha, mi.numero ");
/*  82:    */       
/*  83:    */ 
/*  84: 81 */       sql.append(" , org.razonSocial, org.identificacion, gr.fechaAutorizacion, gr.claveAcceso, oc.numeroResolucionContribuyente,");
/*  85: 82 */       sql.append(" oc.indicadorObligadoContabilidad, gr.direccionMatriz, gr.direccionSucursal, gr.ambiente, gr.tipoEmision, gr.email, gr.autorizacion, '-'");
/*  86: 83 */       sql.append(" ,l.codigo, '','','', '','', '', '', gr.horaSalida, gr.horaLlegada,'','','' ");
/*  87: 84 */       sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  88: 85 */       sql.append(" LEFT JOIN dmi.inventarioProducto ip ");
/*  89: 86 */       sql.append(" LEFT JOIN ip.lote l ");
/*  90: 87 */       sql.append(" LEFT JOIN dmi.movimientoInventario mi ");
/*  91: 88 */       sql.append(" LEFT JOIN mi.direccionEmpresa de ");
/*  92: 89 */       sql.append(" LEFT JOIN mi.empresa emp ");
/*  93: 90 */       sql.append(" LEFT JOIN de.ubicacion ubic ");
/*  94: 91 */       sql.append(" LEFT JOIN de.ciudad ci ");
/*  95: 92 */       sql.append(" LEFT JOIN ci.provincia p ");
/*  96: 93 */       sql.append(" LEFT JOIN p.pais pa ");
/*  97: 94 */       sql.append(" LEFT JOIN mi.bodegaDestino bd ");
/*  98: 95 */       sql.append(" LEFT JOIN bd.ubicacion ub ");
/*  99: 96 */       sql.append(" LEFT JOIN bd.sucursal su, Organizacion org ");
/* 100: 97 */       sql.append(" LEFT JOIN mi.guiaRemision gr ");
/* 101: 98 */       sql.append(" LEFT JOIN gr.ciudadOrigen co ");
/* 102: 99 */       sql.append(" LEFT JOIN gr.ciudadDestino cd ");
/* 103:100 */       sql.append(" LEFT JOIN gr.transportista t ");
/* 104:101 */       sql.append(" LEFT JOIN gr.vehiculo v ");
/* 105:102 */       sql.append(" LEFT JOIN dmi.producto p ");
/* 106:103 */       sql.append(" LEFT JOIN p.unidad u ");
/* 107:104 */       sql.append(" JOIN org.organizacionConfiguracion oc");
/* 108:105 */       sql.append(" WHERE su.idOrganizacion = org.idOrganizacion AND mi.idMovimientoInventario = :idTransferenciaBodega ");
/* 109:    */     }
/* 110:107 */     if (hojaRutaTransportista != null)
/* 111:    */     {
/* 112:108 */       sql = new StringBuffer();
/* 113:109 */       sql.append(" select  dc.descripcion, dc.numero,em.identificacion, em.nombreFiscal, co.nombre, cd.nombre, ");
/* 114:110 */       sql.append(" gr.numero, gr.fecha, gr.tarifa, gr.conductor, gr.licencia, gr.descripcion, ");
/* 115:111 */       sql.append(" gr.placa, t.nombre, t.identificacion, ");
/* 116:112 */       sql.append(" p.codigo, p.nombre, p.peso, p.volumen, u.nombre,");
/* 117:113 */       sql.append(" ddc.cantidad, ddc.descripcion, fc.numero, p.codigoBarras,");
/* 118:114 */       sql.append(" CONCAT(ub.direccion1,' ', ub.direccion2,' ',ub.direccion3,' ',ub.direccion4,' ',ub.direccion5), su.nombre , z.nombre, em.nombreComercial, gr.fechaVigencia, ");
/* 119:115 */       sql.append(" dc.fecha, dc.numero , o.razonSocial, o.identificacion, gr.fechaAutorizacion, gr.claveAcceso, oc.numeroResolucionContribuyente, ");
/* 120:116 */       sql.append(" oc.indicadorObligadoContabilidad, gr.direccionMatriz, gr.direccionSucursal, gr.ambiente, gr.tipoEmision, gr.email, gr.autorizacion, fcSRI.autorizacion ");
/* 121:117 */       sql.append(" , l.codigo,'','','','', '', '', '', gr.horaSalida, gr.horaLlegada,'','','' ");
/* 122:    */       
/* 123:119 */       sql.append(" FROM DetalleDespachoCliente ddc ");
/* 124:120 */       sql.append(" left join ddc.inventarioProducto ip ");
/* 125:121 */       sql.append(" left join ip.lote l ");
/* 126:122 */       sql.append(" left join ddc.despachoCliente dc , Organizacion o, DetalleHojaRuta dhr ");
/* 127:123 */       sql.append(" left join dhr.hojaRuta hr ");
/* 128:124 */       sql.append(" left join dc.direccionEmpresa de ");
/* 129:125 */       sql.append(" left join de.ubicacion ub ");
/* 130:126 */       sql.append(" left join dc.empresa em ");
/* 131:127 */       sql.append(" left join dc.sucursal su ");
/* 132:128 */       sql.append(" left join hr.guiaRemision gr ");
/* 133:129 */       sql.append(" left join gr.ciudadOrigen co ");
/* 134:130 */       sql.append(" left join gr.ciudadDestino cd ");
/* 135:131 */       sql.append(" left join gr.vehiculo v ");
/* 136:132 */       sql.append(" left join v.transportista t ");
/* 137:133 */       sql.append(" left join ddc.producto p ");
/* 138:134 */       sql.append(" left join p.unidad u ");
/* 139:135 */       sql.append(" left join ddc.detalleFacturaCliente dfc ");
/* 140:136 */       sql.append(" left join dfc.facturaCliente fc ");
/* 141:137 */       sql.append(" left join fc.facturaClienteSRI fcSRI ");
/* 142:138 */       sql.append(" left join fc.zona z ");
/* 143:139 */       sql.append(" left join ddc.detalleFacturaCliente dfc ");
/* 144:140 */       sql.append(" left join dfc.facturaCliente fc ");
/* 145:141 */       sql.append(" left join o.organizacionConfiguracion oc ");
/* 146:142 */       sql.append(" where dc.idDespachoCliente in (:listaDespachoCliente) ");
/* 147:143 */       sql.append(" and o.idOrganizacion = dc.idOrganizacion ");
/* 148:144 */       sql.append(" and dc = dhr.despachoCliente ");
/* 149:145 */       sql.append(" order by dc.numero ");
/* 150:    */     }
/* 151:148 */     else if ((idTransferenciaBodega == 0) && (idDespachoCliente == 0) && (idGuiaRemision != 0))
/* 152:    */     {
/* 153:149 */       sql = new StringBuffer();
/* 154:150 */       sql.append("SELECT '', '', COALESCE(em.identificacion, gr.identificacionTransportista), em.nombreFiscal, co.nombre, cd.nombre, ");
/* 155:151 */       sql.append(" gr.numero, gr.fecha, gr.tarifa, gr.conductor, gr.licencia, gr.descripcion, ");
/* 156:152 */       sql.append(" gr.placa, t.nombre, t.identificacion, ");
/* 157:153 */       sql.append(" p.codigo, p.nombre, p.peso, p.volumen, u.nombre, ");
/* 158:154 */       sql.append(" dgr.cantidad, dgr.descripcion, fc.numero, p.codigoBarras, ");
/* 159:155 */       sql.append(" CONCAT(ub.direccion1,' ', ub.direccion2,' ',ub.direccion3,' ',ub.direccion4,' ',ub.direccion5), su.nombre , z.nombre, em.nombreComercial, gr.fechaVigencia, ");
/* 160:156 */       sql.append(" fc.fecha, ''  ");
/* 161:    */       
/* 162:    */ 
/* 163:159 */       sql.append(" , o.razonSocial, o.identificacion, gr.fechaAutorizacion, gr.claveAcceso, oc.numeroResolucionContribuyente,");
/* 164:160 */       sql.append(" oc.indicadorObligadoContabilidad, gr.direccionMatriz, gr.direccionSucursal, gr.ambiente, gr.tipoEmision, gr.email, gr.autorizacion, fcSRI.autorizacion, '' , dgr.valor1, dgr.valor2, dgr.valor3, gr.ruta, '', '', '', gr.horaSalida, gr.horaLlegada,'','','' ");
/* 165:    */       
/* 166:    */ 
/* 167:163 */       sql.append(" FROM DetalleGuiaRemision dgr ");
/* 168:164 */       sql.append(" LEFT JOIN dgr.guiaRemision gr, Organizacion o, Sucursal su ");
/* 169:165 */       sql.append(" LEFT JOIN gr.direccionEmpresa de ");
/* 170:166 */       sql.append(" LEFT JOIN de.ubicacion ub ");
/* 171:167 */       sql.append(" LEFT JOIN gr.empresa em ");
/* 172:168 */       sql.append(" LEFT JOIN gr.ciudadOrigen co ");
/* 173:169 */       sql.append(" LEFT JOIN gr.ciudadDestino cd ");
/* 174:170 */       sql.append(" LEFT JOIN gr.vehiculo v ");
/* 175:171 */       sql.append(" LEFT JOIN v.transportista t ");
/* 176:172 */       sql.append(" LEFT JOIN dgr.producto p ");
/* 177:173 */       sql.append(" LEFT JOIN p.unidad u ");
/* 178:174 */       sql.append(" LEFT JOIN gr.facturaCliente fc ");
/* 179:175 */       sql.append(" LEFT JOIN fc.facturaClienteSRI fcSRI");
/* 180:176 */       sql.append(" LEFT JOIN fc.zona z ");
/* 181:177 */       sql.append(" JOIN o.organizacionConfiguracion oc");
/* 182:178 */       sql.append(" WHERE o.idOrganizacion = gr.idOrganizacion AND su.idSucursal = gr.idSucursal AND gr.idGuiaRemision = :idGuiaRemision ");
/* 183:179 */       sql.append(" ORDER BY dgr.idDetalleGuiaRemision ");
/* 184:    */     }
/* 185:183 */     Query query = this.em.createQuery(sql.toString());
/* 186:184 */     if (idDespachoCliente != 0) {
/* 187:185 */       query.setParameter("idDespachoCliente", Integer.valueOf(idDespachoCliente));
/* 188:    */     }
/* 189:187 */     if (idTransferenciaBodega != 0) {
/* 190:188 */       query.setParameter("idTransferenciaBodega", Integer.valueOf(idTransferenciaBodega));
/* 191:    */     }
/* 192:    */     HojaRuta hr;
/* 193:191 */     if (hojaRutaTransportista != null)
/* 194:    */     {
/* 195:193 */       List<Integer> listaDespachoCliente = new ArrayList();
/* 196:194 */       hr = this.servicioHojaRuta.cargarDetalle(Integer.valueOf(hojaRutaTransportista.getIdHojaRuta()));
/* 197:196 */       for (DetalleHojaRuta dhr : hr.getListaDetalleHojaRuta()) {
/* 198:197 */         listaDespachoCliente.add(Integer.valueOf(dhr.getDespachoCliente().getIdDespachoCliente()));
/* 199:    */       }
/* 200:200 */       query.setParameter("listaDespachoCliente", listaDespachoCliente);
/* 201:    */     }
/* 202:201 */     else if ((idTransferenciaBodega == 0) && (idDespachoCliente == 0) && (idGuiaRemision != 0))
/* 203:    */     {
/* 204:202 */       query.setParameter("idGuiaRemision", Integer.valueOf(idGuiaRemision));
/* 205:    */     }
/* 206:205 */     List<Object[]> resulta = query.getResultList();
/* 207:206 */     for (Object[] objects : resulta) {
/* 208:207 */       if (idTransferenciaBodega != 0) {
/* 209:208 */         objects[27] = null;
/* 210:    */       }
/* 211:    */     }
/* 212:211 */     return resulta;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.ventas.ReporteGuiaRemisionDao
 * JD-Core Version:    0.7.0.1
 */