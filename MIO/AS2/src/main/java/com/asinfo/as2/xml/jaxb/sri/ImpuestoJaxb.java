/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.xml.bind.annotation.XmlElement;
/*   5:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlRootElement(name="impuesto")
/*   9:    */ @XmlType(propOrder={"codigo", "codigoRetencion", "codigoPorcentaje", "tarifa", "baseImponible", "valor", "porcentajeRetener", "valorRetenido", "codDocSustento", "numDocSustento", "fechaEmisionDocSustento"})
/*  10:    */ public class ImpuestoJaxb
/*  11:    */ {
/*  12:    */   private String codigo;
/*  13:    */   private Integer codigoPorcentaje;
/*  14:    */   private BigDecimal baseImponible;
/*  15:    */   private BigDecimal valor;
/*  16:    */   private BigDecimal tarifa;
/*  17:    */   private String codigoRetencion;
/*  18:    */   private BigDecimal porcentajeRetener;
/*  19:    */   private BigDecimal valorRetenido;
/*  20:    */   private String codDocSustento;
/*  21:    */   private String numDocSustento;
/*  22:    */   private String fechaEmisionDocSustento;
/*  23:    */   
/*  24:    */   @XmlElement(name="codigo")
/*  25:    */   public String getCodigo()
/*  26:    */   {
/*  27: 43 */     return this.codigo;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setCodigo(String codigo)
/*  31:    */   {
/*  32: 47 */     this.codigo = codigo;
/*  33:    */   }
/*  34:    */   
/*  35:    */   @XmlElement(name="codigoPorcentaje")
/*  36:    */   public Integer getCodigoPorcentaje()
/*  37:    */   {
/*  38: 52 */     return this.codigoPorcentaje;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setCodigoPorcentaje(Integer codigoPorcentaje)
/*  42:    */   {
/*  43: 56 */     this.codigoPorcentaje = codigoPorcentaje;
/*  44:    */   }
/*  45:    */   
/*  46:    */   @XmlElement(name="baseImponible")
/*  47:    */   public BigDecimal getBaseImponible()
/*  48:    */   {
/*  49: 61 */     return this.baseImponible;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setBaseImponible(BigDecimal baseImponible)
/*  53:    */   {
/*  54: 65 */     this.baseImponible = baseImponible;
/*  55:    */   }
/*  56:    */   
/*  57:    */   @XmlElement(name="valor")
/*  58:    */   public BigDecimal getValor()
/*  59:    */   {
/*  60: 70 */     return this.valor;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setValor(BigDecimal valor)
/*  64:    */   {
/*  65: 74 */     this.valor = valor;
/*  66:    */   }
/*  67:    */   
/*  68:    */   @XmlElement(name="tarifa")
/*  69:    */   public BigDecimal getTarifa()
/*  70:    */   {
/*  71: 79 */     return this.tarifa;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setTarifa(BigDecimal tarifa)
/*  75:    */   {
/*  76: 83 */     this.tarifa = tarifa;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @XmlElement(name="codigoRetencion")
/*  80:    */   public String getCodigoRetencion()
/*  81:    */   {
/*  82: 88 */     return this.codigoRetencion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCodigoRetencion(String codigoRetencion)
/*  86:    */   {
/*  87: 92 */     this.codigoRetencion = codigoRetencion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @XmlElement(name="porcentajeRetener")
/*  91:    */   public BigDecimal getPorcentajeRetener()
/*  92:    */   {
/*  93: 97 */     return this.porcentajeRetener;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setPorcentajeRetener(BigDecimal porcentajeRetener)
/*  97:    */   {
/*  98:101 */     this.porcentajeRetener = porcentajeRetener;
/*  99:    */   }
/* 100:    */   
/* 101:    */   @XmlElement(name="valorRetenido")
/* 102:    */   public BigDecimal getValorRetenido()
/* 103:    */   {
/* 104:106 */     return this.valorRetenido;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setValorRetenido(BigDecimal valorRetenido)
/* 108:    */   {
/* 109:110 */     this.valorRetenido = valorRetenido;
/* 110:    */   }
/* 111:    */   
/* 112:    */   @XmlElement(name="codDocSustento")
/* 113:    */   public String getCodDocSustento()
/* 114:    */   {
/* 115:115 */     return this.codDocSustento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCodDocSustento(String codDocSustento)
/* 119:    */   {
/* 120:119 */     this.codDocSustento = codDocSustento;
/* 121:    */   }
/* 122:    */   
/* 123:    */   @XmlElement(name="numDocSustento")
/* 124:    */   public String getNumDocSustento()
/* 125:    */   {
/* 126:124 */     return this.numDocSustento;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setNumDocSustento(String numDocSustento)
/* 130:    */   {
/* 131:128 */     this.numDocSustento = numDocSustento;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @XmlElement(name="fechaEmisionDocSustento")
/* 135:    */   public String getFechaEmisionDocSustento()
/* 136:    */   {
/* 137:133 */     return this.fechaEmisionDocSustento;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento)
/* 141:    */   {
/* 142:137 */     this.fechaEmisionDocSustento = fechaEmisionDocSustento;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.ImpuestoJaxb
 * JD-Core Version:    0.7.0.1
 */