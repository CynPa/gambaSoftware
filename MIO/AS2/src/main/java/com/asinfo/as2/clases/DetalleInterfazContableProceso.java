/*    1:     */ package com.asinfo.as2.clases;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.CuentaContable;
/*    4:     */ import com.asinfo.as2.entities.DimensionContable;
/*    5:     */ import java.math.BigDecimal;
/*    6:     */ import javax.persistence.Transient;
/*    7:     */ 
/*    8:     */ public class DetalleInterfazContableProceso
/*    9:     */ {
/*   10:     */   @Transient
/*   11:     */   private Integer documento;
/*   12:     */   @Transient
/*   13:  30 */   private String nombreDocumento = "";
/*   14:     */   @Transient
/*   15:     */   private Integer sucursal;
/*   16:     */   @Transient
/*   17:  35 */   private String nombreSucursal = "";
/*   18:     */   @Transient
/*   19:     */   private Integer categoriaEmpresa;
/*   20:     */   @Transient
/*   21:  40 */   private String nombreCategoriaEmpresa = "";
/*   22:     */   @Transient
/*   23:     */   private Integer cliente;
/*   24:     */   @Transient
/*   25:  45 */   private String nombreCliente = "";
/*   26:     */   @Transient
/*   27:     */   private Integer proveedor;
/*   28:     */   @Transient
/*   29:  50 */   private String nombreProveedor = "";
/*   30:     */   @Transient
/*   31:     */   private Integer categoriaProducto;
/*   32:  55 */   private String nombreCategoriaProducto = "";
/*   33:     */   @Transient
/*   34:     */   private Integer subcategoriaProducto;
/*   35:     */   @Transient
/*   36:  59 */   private String nombreSubcategoriaProducto = "";
/*   37:     */   @Transient
/*   38:     */   private Integer producto;
/*   39:     */   @Transient
/*   40:  64 */   private String nombreProducto = "";
/*   41:     */   @Transient
/*   42:     */   private Integer bodega;
/*   43:     */   @Transient
/*   44:  69 */   private String nombreBodega = "";
/*   45:     */   @Transient
/*   46:     */   private Integer canal;
/*   47:     */   @Transient
/*   48:  74 */   private String nombreCanal = "";
/*   49:     */   @Transient
/*   50:     */   private Integer subcliente;
/*   51:     */   @Transient
/*   52:  79 */   private String nombreSubcliente = "";
/*   53:     */   @Transient
/*   54:     */   private Integer subproveedor;
/*   55:     */   @Transient
/*   56:  84 */   private String nombreSubproveedor = "";
/*   57:     */   @Transient
/*   58:     */   private Integer zona;
/*   59:     */   @Transient
/*   60:  89 */   private String nombreZona = "";
/*   61:     */   @Transient
/*   62:     */   private Integer motivoNotaCreditoCliente;
/*   63:     */   @Transient
/*   64:  94 */   private String nombreMotivoNotaCreditoCliente = "";
/*   65:     */   @Transient
/*   66:     */   private Integer motivoNotaCreditoProveedor;
/*   67:     */   @Transient
/*   68:  99 */   private String nombreMotivoNotaCreditoProveedor = "";
/*   69:     */   @Transient
/*   70:     */   private Integer destinoCosto;
/*   71:     */   @Transient
/*   72: 104 */   private String nombreDestinoCosto = "";
/*   73:     */   @Transient
/*   74:     */   private Integer motivoAjusteInventario;
/*   75:     */   @Transient
/*   76: 109 */   private String nombreMotivoAjusteInventario = "";
/*   77:     */   @Transient
/*   78:     */   private Integer motivoBajaActivo;
/*   79:     */   @Transient
/*   80: 114 */   private String nombreMotivoBajaActivo = "";
/*   81:     */   @Transient
/*   82:     */   private Integer categoriaActivo;
/*   83:     */   @Transient
/*   84: 119 */   private String nombreCategoriaActivo = "";
/*   85:     */   @Transient
/*   86:     */   private Integer subcategoriaActivo;
/*   87:     */   @Transient
/*   88: 124 */   private String nombreSubcategoriaActivo = "";
/*   89:     */   @Transient
/*   90:     */   private Integer activoFijo;
/*   91:     */   @Transient
/*   92: 129 */   private String nombreActivoFijo = "";
/*   93:     */   @Transient
/*   94:     */   private Integer empleado;
/*   95:     */   @Transient
/*   96: 134 */   private String nombreEmpleado = "";
/*   97:     */   @Transient
/*   98:     */   private Integer rubro;
/*   99:     */   @Transient
/*  100: 139 */   private String nombreRubro = "";
/*  101:     */   @Transient
/*  102:     */   private Integer departamento;
/*  103:     */   @Transient
/*  104: 144 */   private String nombreDepartamento = "";
/*  105:     */   @Transient
/*  106:     */   private Integer impuesto;
/*  107:     */   @Transient
/*  108: 149 */   private String nombreImpuesto = "";
/*  109:     */   @Transient
/*  110:     */   private Integer CategoriaImpuesto;
/*  111:     */   @Transient
/*  112: 154 */   private String nombreCategoriaImpuesto = "";
/*  113:     */   @Transient
/*  114:     */   private Integer centroTrabajo;
/*  115:     */   @Transient
/*  116: 159 */   private String nombreCentroTrabajo = "";
/*  117:     */   @Transient
/*  118:     */   private Integer centroConsumo;
/*  119:     */   @Transient
/*  120: 164 */   private String nombreCentroConsumo = "";
/*  121:     */   @Transient
/*  122:     */   private Integer conceptoContable;
/*  123:     */   @Transient
/*  124: 169 */   private String nombreConceptoContable = "";
/*  125:     */   @Transient
/*  126:     */   private Integer tipoAmortizacion;
/*  127:     */   @Transient
/*  128:     */   private String nombreTipoAmortizacion;
/*  129:     */   @Transient
/*  130:     */   private String descripcion;
/*  131:     */   @Transient
/*  132:     */   private boolean seleccionadoCriterioContabilizacion;
/*  133:     */   @Transient
/*  134:     */   private boolean seleccionadoCriterioDistribucion;
/*  135:     */   @Transient
/*  136: 186 */   private BigDecimal valor = BigDecimal.ZERO;
/*  137:     */   @Transient
/*  138: 189 */   private BigDecimal impuestosComercializadora = BigDecimal.ZERO;
/*  139:     */   @Transient
/*  140: 192 */   private BigDecimal proporcion = BigDecimal.ZERO;
/*  141:     */   @Transient
/*  142:     */   private CuentaContable cuentaContable;
/*  143:     */   @Transient
/*  144:     */   private DimensionContable dimensionContable1;
/*  145:     */   @Transient
/*  146:     */   private DimensionContable dimensionContable2;
/*  147:     */   @Transient
/*  148:     */   private DimensionContable dimensionContable3;
/*  149:     */   @Transient
/*  150:     */   private DimensionContable dimensionContable4;
/*  151:     */   @Transient
/*  152:     */   private DimensionContable dimensionContable5;
/*  153:     */   @Transient
/*  154: 213 */   private String agrupamiento = "";
/*  155:     */   @Transient
/*  156: 216 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/*  157:     */   @Transient
/*  158:     */   private Integer idFacturaCliente;
/*  159:     */   
/*  160:     */   public DetalleInterfazContableProceso() {}
/*  161:     */   
/*  162:     */   public DetalleInterfazContableProceso(Integer documento, Integer sucursal, Integer categoriaEmpresa, Integer cliente, Integer proveedor, Integer categoriaProducto, Integer subcategoriaProducto, Integer producto, Integer bodega, Integer canal, Integer subcliente, Integer subproveedor, Integer zona, Integer motivoNotaCreditoCliente, Integer motivoNotaCreditoProveedor, Integer motivoAjusteInventario, Integer motivoBajaActivo, Integer categoriaActivo, Integer subcategoriaActivo, Integer activoFijo, Integer empleado, Integer rubro, Integer impuesto, String descripcion, BigDecimal valor)
/*  163:     */   {
/*  164: 256 */     this.documento = documento;
/*  165: 257 */     this.sucursal = sucursal;
/*  166: 258 */     this.categoriaEmpresa = categoriaEmpresa;
/*  167: 259 */     this.cliente = cliente;
/*  168: 260 */     this.proveedor = proveedor;
/*  169: 261 */     this.categoriaProducto = categoriaProducto;
/*  170: 262 */     this.subcategoriaProducto = subcategoriaProducto;
/*  171: 263 */     this.producto = producto;
/*  172: 264 */     this.bodega = bodega;
/*  173: 265 */     this.canal = canal;
/*  174: 266 */     this.subcliente = subcliente;
/*  175: 267 */     this.subproveedor = subproveedor;
/*  176: 268 */     this.zona = zona;
/*  177: 269 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/*  178: 270 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/*  179: 271 */     this.motivoAjusteInventario = motivoAjusteInventario;
/*  180: 272 */     this.motivoBajaActivo = motivoBajaActivo;
/*  181: 273 */     this.categoriaActivo = categoriaActivo;
/*  182: 274 */     this.subcategoriaActivo = subcategoriaActivo;
/*  183: 275 */     this.activoFijo = activoFijo;
/*  184: 276 */     this.empleado = empleado;
/*  185: 277 */     this.rubro = rubro;
/*  186: 278 */     this.impuesto = impuesto;
/*  187: 279 */     this.descripcion = descripcion;
/*  188: 280 */     this.valor = valor;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public Integer getDocumento()
/*  192:     */   {
/*  193: 289 */     return this.documento;
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void setDocumento(Integer documento)
/*  197:     */   {
/*  198: 299 */     this.documento = documento;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public Integer getSucursal()
/*  202:     */   {
/*  203: 308 */     return this.sucursal;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void setSucursal(Integer sucursal)
/*  207:     */   {
/*  208: 318 */     this.sucursal = sucursal;
/*  209:     */   }
/*  210:     */   
/*  211:     */   public Integer getCategoriaEmpresa()
/*  212:     */   {
/*  213: 327 */     return this.categoriaEmpresa;
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void setCategoriaEmpresa(Integer categoriaEmpresa)
/*  217:     */   {
/*  218: 337 */     this.categoriaEmpresa = categoriaEmpresa;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public Integer getCliente()
/*  222:     */   {
/*  223: 346 */     return this.cliente;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void setCliente(Integer cliente)
/*  227:     */   {
/*  228: 356 */     this.cliente = cliente;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public Integer getProveedor()
/*  232:     */   {
/*  233: 365 */     return this.proveedor;
/*  234:     */   }
/*  235:     */   
/*  236:     */   public void setProveedor(Integer proveedor)
/*  237:     */   {
/*  238: 375 */     this.proveedor = proveedor;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public Integer getCategoriaProducto()
/*  242:     */   {
/*  243: 384 */     return this.categoriaProducto;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void setCategoriaProducto(Integer categoriaProducto)
/*  247:     */   {
/*  248: 394 */     this.categoriaProducto = categoriaProducto;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public Integer getSubcategoriaProducto()
/*  252:     */   {
/*  253: 403 */     return this.subcategoriaProducto;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void setSubcategoriaProducto(Integer subcategoriaProducto)
/*  257:     */   {
/*  258: 413 */     this.subcategoriaProducto = subcategoriaProducto;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public Integer getProducto()
/*  262:     */   {
/*  263: 422 */     return this.producto;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public void setProducto(Integer producto)
/*  267:     */   {
/*  268: 432 */     this.producto = producto;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public Integer getBodega()
/*  272:     */   {
/*  273: 441 */     return this.bodega;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public void setBodega(Integer bodega)
/*  277:     */   {
/*  278: 451 */     this.bodega = bodega;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public Integer getCanal()
/*  282:     */   {
/*  283: 460 */     return this.canal;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void setCanal(Integer canal)
/*  287:     */   {
/*  288: 470 */     this.canal = canal;
/*  289:     */   }
/*  290:     */   
/*  291:     */   public Integer getSubcliente()
/*  292:     */   {
/*  293: 479 */     return this.subcliente;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void setSubcliente(Integer subcliente)
/*  297:     */   {
/*  298: 489 */     this.subcliente = subcliente;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public Integer getSubproveedor()
/*  302:     */   {
/*  303: 498 */     return this.subproveedor;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public void setSubproveedor(Integer subproveedor)
/*  307:     */   {
/*  308: 508 */     this.subproveedor = subproveedor;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public Integer getZona()
/*  312:     */   {
/*  313: 517 */     return this.zona;
/*  314:     */   }
/*  315:     */   
/*  316:     */   public void setZona(Integer zona)
/*  317:     */   {
/*  318: 527 */     this.zona = zona;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public Integer getMotivoNotaCreditoCliente()
/*  322:     */   {
/*  323: 536 */     return this.motivoNotaCreditoCliente;
/*  324:     */   }
/*  325:     */   
/*  326:     */   public void setMotivoNotaCreditoCliente(Integer motivoNotaCreditoCliente)
/*  327:     */   {
/*  328: 546 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/*  329:     */   }
/*  330:     */   
/*  331:     */   public Integer getMotivoNotaCreditoProveedor()
/*  332:     */   {
/*  333: 555 */     return this.motivoNotaCreditoProveedor;
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void setMotivoNotaCreditoProveedor(Integer motivoNotaCreditoProveedor)
/*  337:     */   {
/*  338: 565 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public Integer getMotivoAjusteInventario()
/*  342:     */   {
/*  343: 574 */     return this.motivoAjusteInventario;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void setMotivoAjusteInventario(Integer motivoAjusteInventario)
/*  347:     */   {
/*  348: 584 */     this.motivoAjusteInventario = motivoAjusteInventario;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public Integer getMotivoBajaActivo()
/*  352:     */   {
/*  353: 593 */     return this.motivoBajaActivo;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public void setMotivoBajaActivo(Integer motivoBajaActivo)
/*  357:     */   {
/*  358: 603 */     this.motivoBajaActivo = motivoBajaActivo;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public Integer getCategoriaActivo()
/*  362:     */   {
/*  363: 612 */     return this.categoriaActivo;
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void setCategoriaActivo(Integer categoriaActivo)
/*  367:     */   {
/*  368: 622 */     this.categoriaActivo = categoriaActivo;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public Integer getSubcategoriaActivo()
/*  372:     */   {
/*  373: 631 */     return this.subcategoriaActivo;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void setSubcategoriaActivo(Integer subcategoriaActivo)
/*  377:     */   {
/*  378: 641 */     this.subcategoriaActivo = subcategoriaActivo;
/*  379:     */   }
/*  380:     */   
/*  381:     */   public Integer getActivoFijo()
/*  382:     */   {
/*  383: 650 */     return this.activoFijo;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public void setActivoFijo(Integer activoFijo)
/*  387:     */   {
/*  388: 660 */     this.activoFijo = activoFijo;
/*  389:     */   }
/*  390:     */   
/*  391:     */   public Integer getEmpleado()
/*  392:     */   {
/*  393: 669 */     return this.empleado;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public void setEmpleado(Integer empleado)
/*  397:     */   {
/*  398: 679 */     this.empleado = empleado;
/*  399:     */   }
/*  400:     */   
/*  401:     */   public Integer getRubro()
/*  402:     */   {
/*  403: 688 */     return this.rubro;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void setRubro(Integer rubro)
/*  407:     */   {
/*  408: 698 */     this.rubro = rubro;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public boolean isSeleccionadoCriterioContabilizacion()
/*  412:     */   {
/*  413: 707 */     return this.seleccionadoCriterioContabilizacion;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setSeleccionadoCriterioContabilizacion(boolean seleccionadoCriterioContabilizacion)
/*  417:     */   {
/*  418: 717 */     this.seleccionadoCriterioContabilizacion = seleccionadoCriterioContabilizacion;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public boolean isSeleccionadoCriterioDistribucion()
/*  422:     */   {
/*  423: 726 */     return this.seleccionadoCriterioDistribucion;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setSeleccionadoCriterioDistribucion(boolean seleccionadoCriterioDistribucion)
/*  427:     */   {
/*  428: 736 */     this.seleccionadoCriterioDistribucion = seleccionadoCriterioDistribucion;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public BigDecimal getValor()
/*  432:     */   {
/*  433: 745 */     return this.valor;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setValor(BigDecimal valor)
/*  437:     */   {
/*  438: 755 */     this.valor = valor;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public BigDecimal getProporcion()
/*  442:     */   {
/*  443: 764 */     return this.proporcion;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setProporcion(BigDecimal proporcion)
/*  447:     */   {
/*  448: 774 */     this.proporcion = proporcion;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public Integer getImpuesto()
/*  452:     */   {
/*  453: 783 */     return this.impuesto;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void setImpuesto(Integer impuesto)
/*  457:     */   {
/*  458: 793 */     this.impuesto = impuesto;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public Integer getTipoAmortizacion()
/*  462:     */   {
/*  463: 800 */     return this.tipoAmortizacion;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void setTipoAmortizacion(Integer tipoAmortizacion)
/*  467:     */   {
/*  468: 808 */     this.tipoAmortizacion = tipoAmortizacion;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public String getNombreTipoAmortizacion()
/*  472:     */   {
/*  473: 815 */     return this.nombreTipoAmortizacion;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void setNombreTipoAmortizacion(String nombreTipoAmortizacion)
/*  477:     */   {
/*  478: 823 */     this.nombreTipoAmortizacion = nombreTipoAmortizacion;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public String getDescripcion()
/*  482:     */   {
/*  483: 827 */     return this.descripcion;
/*  484:     */   }
/*  485:     */   
/*  486:     */   public void setDescripcion(String descripcion)
/*  487:     */   {
/*  488: 831 */     this.descripcion = descripcion;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public Integer getCentroTrabajo()
/*  492:     */   {
/*  493: 835 */     return this.centroTrabajo;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void setCentroTrabajo(Integer centroTrabajo)
/*  497:     */   {
/*  498: 839 */     this.centroTrabajo = centroTrabajo;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public Integer getCentroConsumo()
/*  502:     */   {
/*  503: 843 */     return this.centroConsumo;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void setCentroConsumo(Integer centroConsumo)
/*  507:     */   {
/*  508: 847 */     this.centroConsumo = centroConsumo;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public Integer getConceptoContable()
/*  512:     */   {
/*  513: 851 */     return this.conceptoContable;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void setConceptoContable(Integer conceptoContable)
/*  517:     */   {
/*  518: 855 */     this.conceptoContable = conceptoContable;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public Integer getDepartamento()
/*  522:     */   {
/*  523: 859 */     return this.departamento;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void setDepartamento(Integer departamento)
/*  527:     */   {
/*  528: 863 */     this.departamento = departamento;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public Integer getDestinoCosto()
/*  532:     */   {
/*  533: 867 */     return this.destinoCosto;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public void setDestinoCosto(Integer destinoCosto)
/*  537:     */   {
/*  538: 871 */     this.destinoCosto = destinoCosto;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public String getNombreDocumento()
/*  542:     */   {
/*  543: 880 */     return this.nombreDocumento;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setNombreDocumento(String nombreDocumento)
/*  547:     */   {
/*  548: 890 */     this.nombreDocumento = nombreDocumento;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public String getNombreSucursal()
/*  552:     */   {
/*  553: 899 */     return this.nombreSucursal;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setNombreSucursal(String nombreSucursal)
/*  557:     */   {
/*  558: 909 */     this.nombreSucursal = nombreSucursal;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public String getNombreCategoriaEmpresa()
/*  562:     */   {
/*  563: 918 */     return this.nombreCategoriaEmpresa;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void setNombreCategoriaEmpresa(String nombreCategoriaEmpresa)
/*  567:     */   {
/*  568: 928 */     this.nombreCategoriaEmpresa = nombreCategoriaEmpresa;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public String getNombreEmpleado()
/*  572:     */   {
/*  573: 937 */     return this.nombreEmpleado;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setNombreEmpleado(String nombreEmpleado)
/*  577:     */   {
/*  578: 947 */     this.nombreEmpleado = nombreEmpleado;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public String getNombreRubro()
/*  582:     */   {
/*  583: 956 */     return this.nombreRubro;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setNombreRubro(String nombreRubro)
/*  587:     */   {
/*  588: 966 */     this.nombreRubro = nombreRubro;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public String getNombreDepartamento()
/*  592:     */   {
/*  593: 975 */     return this.nombreDepartamento;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setNombreDepartamento(String nombreDepartamento)
/*  597:     */   {
/*  598: 985 */     this.nombreDepartamento = nombreDepartamento;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public String getNombreCliente()
/*  602:     */   {
/*  603: 994 */     return this.nombreCliente;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setNombreCliente(String nombreCliente)
/*  607:     */   {
/*  608:1004 */     this.nombreCliente = nombreCliente;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public String getNombreProveedor()
/*  612:     */   {
/*  613:1013 */     return this.nombreProveedor;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setNombreProveedor(String nombreProveedor)
/*  617:     */   {
/*  618:1023 */     this.nombreProveedor = nombreProveedor;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public String getNombreCategoriaProducto()
/*  622:     */   {
/*  623:1032 */     return this.nombreCategoriaProducto;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setNombreCategoriaProducto(String nombreCategoriaProducto)
/*  627:     */   {
/*  628:1042 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public String getNombreSubcategoriaProducto()
/*  632:     */   {
/*  633:1051 */     return this.nombreSubcategoriaProducto;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void setNombreSubcategoriaProducto(String nombreSubcategoriaProducto)
/*  637:     */   {
/*  638:1061 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public String getNombreProducto()
/*  642:     */   {
/*  643:1070 */     return this.nombreProducto;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setNombreProducto(String nombreProducto)
/*  647:     */   {
/*  648:1080 */     this.nombreProducto = nombreProducto;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public String getNombreBodega()
/*  652:     */   {
/*  653:1089 */     return this.nombreBodega;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setNombreBodega(String nombreBodega)
/*  657:     */   {
/*  658:1099 */     this.nombreBodega = nombreBodega;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public String getNombreCanal()
/*  662:     */   {
/*  663:1108 */     return this.nombreCanal;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setNombreCanal(String nombreCanal)
/*  667:     */   {
/*  668:1118 */     this.nombreCanal = nombreCanal;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public String getNombreSubcliente()
/*  672:     */   {
/*  673:1127 */     return this.nombreSubcliente;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void setNombreSubcliente(String nombreSubcliente)
/*  677:     */   {
/*  678:1137 */     this.nombreSubcliente = nombreSubcliente;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public String getNombreSubproveedor()
/*  682:     */   {
/*  683:1146 */     return this.nombreSubproveedor;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void setNombreSubproveedor(String nombreSubproveedor)
/*  687:     */   {
/*  688:1156 */     this.nombreSubproveedor = nombreSubproveedor;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public String getNombreZona()
/*  692:     */   {
/*  693:1165 */     return this.nombreZona;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void setNombreZona(String nombreZona)
/*  697:     */   {
/*  698:1175 */     this.nombreZona = nombreZona;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public String getNombreMotivoNotaCreditoCliente()
/*  702:     */   {
/*  703:1184 */     return this.nombreMotivoNotaCreditoCliente;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void setNombreMotivoNotaCreditoCliente(String nombreMotivoNotaCreditoCliente)
/*  707:     */   {
/*  708:1194 */     this.nombreMotivoNotaCreditoCliente = nombreMotivoNotaCreditoCliente;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public String getNombreMotivoNotaCreditoProveedor()
/*  712:     */   {
/*  713:1203 */     return this.nombreMotivoNotaCreditoProveedor;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void setNombreMotivoNotaCreditoProveedor(String nombreMotivoNotaCreditoProveedor)
/*  717:     */   {
/*  718:1213 */     this.nombreMotivoNotaCreditoProveedor = nombreMotivoNotaCreditoProveedor;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public String getNombreDestinoCosto()
/*  722:     */   {
/*  723:1222 */     return this.nombreDestinoCosto;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public void setNombreDestinoCosto(String nombreDestinoCosto)
/*  727:     */   {
/*  728:1232 */     this.nombreDestinoCosto = nombreDestinoCosto;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String getNombreMotivoAjusteInventario()
/*  732:     */   {
/*  733:1241 */     return this.nombreMotivoAjusteInventario;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public void setNombreMotivoAjusteInventario(String nombreMotivoAjusteInventario)
/*  737:     */   {
/*  738:1251 */     this.nombreMotivoAjusteInventario = nombreMotivoAjusteInventario;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public String getNombreMotivoBajaActivo()
/*  742:     */   {
/*  743:1260 */     return this.nombreMotivoBajaActivo;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void setNombreMotivoBajaActivo(String nombreMotivoBajaActivo)
/*  747:     */   {
/*  748:1270 */     this.nombreMotivoBajaActivo = nombreMotivoBajaActivo;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public String getNombreCategoriaActivo()
/*  752:     */   {
/*  753:1279 */     return this.nombreCategoriaActivo;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setNombreCategoriaActivo(String nombreCategoriaActivo)
/*  757:     */   {
/*  758:1289 */     this.nombreCategoriaActivo = nombreCategoriaActivo;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String getNombreSubcategoriaActivo()
/*  762:     */   {
/*  763:1298 */     return this.nombreSubcategoriaActivo;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public void setNombreSubcategoriaActivo(String nombreSubcategoriaActivo)
/*  767:     */   {
/*  768:1308 */     this.nombreSubcategoriaActivo = nombreSubcategoriaActivo;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public String getNombreActivoFijo()
/*  772:     */   {
/*  773:1317 */     return this.nombreActivoFijo;
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void setNombreActivoFijo(String nombreActivoFijo)
/*  777:     */   {
/*  778:1327 */     this.nombreActivoFijo = nombreActivoFijo;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public String getNombreImpuesto()
/*  782:     */   {
/*  783:1336 */     return this.nombreImpuesto;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public void setNombreImpuesto(String nombreImpuesto)
/*  787:     */   {
/*  788:1346 */     this.nombreImpuesto = nombreImpuesto;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public String getNombreCentroTrabajo()
/*  792:     */   {
/*  793:1355 */     return this.nombreCentroTrabajo;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public void setNombreCentroTrabajo(String nombreCentroTrabajo)
/*  797:     */   {
/*  798:1365 */     this.nombreCentroTrabajo = nombreCentroTrabajo;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public String getNombreCentroConsumo()
/*  802:     */   {
/*  803:1374 */     return this.nombreCentroConsumo;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void setNombreCentroConsumo(String nombreCentroConsumo)
/*  807:     */   {
/*  808:1384 */     this.nombreCentroConsumo = nombreCentroConsumo;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public String getNombreConceptoContable()
/*  812:     */   {
/*  813:1393 */     return this.nombreConceptoContable;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void setNombreConceptoContable(String nombreConceptoContable)
/*  817:     */   {
/*  818:1403 */     this.nombreConceptoContable = nombreConceptoContable;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public DimensionContable getDimensionContable1()
/*  822:     */   {
/*  823:1407 */     return this.dimensionContable1;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void setDimensionContable1(DimensionContable dimensionContable1)
/*  827:     */   {
/*  828:1411 */     this.dimensionContable1 = dimensionContable1;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public DimensionContable getDimensionContable2()
/*  832:     */   {
/*  833:1415 */     return this.dimensionContable2;
/*  834:     */   }
/*  835:     */   
/*  836:     */   public void setDimensionContable2(DimensionContable dimensionContable2)
/*  837:     */   {
/*  838:1419 */     this.dimensionContable2 = dimensionContable2;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public DimensionContable getDimensionContable3()
/*  842:     */   {
/*  843:1423 */     return this.dimensionContable3;
/*  844:     */   }
/*  845:     */   
/*  846:     */   public void setDimensionContable3(DimensionContable dimensionContable3)
/*  847:     */   {
/*  848:1427 */     this.dimensionContable3 = dimensionContable3;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public DimensionContable getDimensionContable4()
/*  852:     */   {
/*  853:1431 */     return this.dimensionContable4;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public void setDimensionContable4(DimensionContable dimensionContable4)
/*  857:     */   {
/*  858:1435 */     this.dimensionContable4 = dimensionContable4;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public DimensionContable getDimensionContable5()
/*  862:     */   {
/*  863:1439 */     return this.dimensionContable5;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public void setDimensionContable5(DimensionContable dimensionContable5)
/*  867:     */   {
/*  868:1443 */     this.dimensionContable5 = dimensionContable5;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public CuentaContable getCuentaContable()
/*  872:     */   {
/*  873:1447 */     return this.cuentaContable;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void setCuentaContable(CuentaContable cuentaContable)
/*  877:     */   {
/*  878:1451 */     this.cuentaContable = cuentaContable;
/*  879:     */   }
/*  880:     */   
/*  881:     */   public String getAgrupamiento()
/*  882:     */   {
/*  883:1455 */     return this.agrupamiento;
/*  884:     */   }
/*  885:     */   
/*  886:     */   public void setAgrupamiento(String agrupamiento)
/*  887:     */   {
/*  888:1459 */     this.agrupamiento = agrupamiento;
/*  889:     */   }
/*  890:     */   
/*  891:     */   public BigDecimal getImpuestosComercializadora()
/*  892:     */   {
/*  893:1463 */     return this.impuestosComercializadora;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void setImpuestosComercializadora(BigDecimal impuestosComercializadora)
/*  897:     */   {
/*  898:1467 */     this.impuestosComercializadora = impuestosComercializadora;
/*  899:     */   }
/*  900:     */   
/*  901:     */   public String getNombreCategoriaImpuesto()
/*  902:     */   {
/*  903:1471 */     return this.nombreCategoriaImpuesto;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setNombreCategoriaImpuesto(String nombreCategoriaImpuesto)
/*  907:     */   {
/*  908:1475 */     this.nombreCategoriaImpuesto = nombreCategoriaImpuesto;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public Integer getCategoriaImpuesto()
/*  912:     */   {
/*  913:1479 */     return this.CategoriaImpuesto;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void setCategoriaImpuesto(Integer categoriaImpuesto)
/*  917:     */   {
/*  918:1483 */     this.CategoriaImpuesto = categoriaImpuesto;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public BigDecimal getDescuentoImpuesto()
/*  922:     */   {
/*  923:1487 */     return this.descuentoImpuesto;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/*  927:     */   {
/*  928:1491 */     this.descuentoImpuesto = descuentoImpuesto;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public Integer getIdFacturaCliente()
/*  932:     */   {
/*  933:1495 */     return this.idFacturaCliente;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  937:     */   {
/*  938:1499 */     this.idFacturaCliente = idFacturaCliente;
/*  939:     */   }
/*  940:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProceso
 * JD-Core Version:    0.7.0.1
 */