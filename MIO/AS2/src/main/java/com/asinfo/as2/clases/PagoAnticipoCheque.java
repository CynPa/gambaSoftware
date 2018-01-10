/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.Temporal;
/*  12:    */ import javax.persistence.TemporalType;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tmp_pago_anticipo_cliente")
/*  16:    */ public class PagoAnticipoCheque
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @Column(name="id_pago_anticipo_cheque")
/*  22:    */   private int idPagoAnticipoCheque;
/*  23:    */   @Column(name="id_proceso")
/*  24:    */   private int idProceso;
/*  25:    */   @Column(name="documento_base")
/*  26:    */   private DocumentoBase documentoBase;
/*  27:    */   @Column(name="documento")
/*  28:    */   private String documento;
/*  29:    */   @Column(name="numero")
/*  30:    */   private String numero;
/*  31:    */   @Temporal(TemporalType.DATE)
/*  32:    */   @Column(name="fecha")
/*  33:    */   private Date fecha;
/*  34:    */   @Column(name="proveedor")
/*  35:    */   private String proveedor;
/*  36:    */   @Column(name="valor")
/*  37:    */   private BigDecimal valor;
/*  38:    */   
/*  39:    */   public PagoAnticipoCheque() {}
/*  40:    */   
/*  41:    */   public PagoAnticipoCheque(int idProceso, DocumentoBase documentoBase, String documento, String numero, Date fecha, String proveedor, BigDecimal valor)
/*  42:    */   {
/*  43: 84 */     this.idProceso = idProceso;
/*  44: 85 */     this.documentoBase = documentoBase;
/*  45: 86 */     this.documento = documento;
/*  46: 87 */     this.numero = numero;
/*  47: 88 */     this.fecha = fecha;
/*  48: 89 */     this.proveedor = proveedor;
/*  49: 90 */     this.valor = valor;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdPagoAnticipoCheque()
/*  53:    */   {
/*  54:101 */     return this.idPagoAnticipoCheque;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdPagoAnticipoCheque(int idPagoAnticipoCheque)
/*  58:    */   {
/*  59:111 */     this.idPagoAnticipoCheque = idPagoAnticipoCheque;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdProceso()
/*  63:    */   {
/*  64:120 */     return this.idProceso;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdProceso(int idProceso)
/*  68:    */   {
/*  69:130 */     this.idProceso = idProceso;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getNumero()
/*  73:    */   {
/*  74:139 */     return this.numero;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setNumero(String numero)
/*  78:    */   {
/*  79:149 */     this.numero = numero;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Date getFecha()
/*  83:    */   {
/*  84:158 */     return this.fecha;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setFecha(Date fecha)
/*  88:    */   {
/*  89:168 */     this.fecha = fecha;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getProveedor()
/*  93:    */   {
/*  94:177 */     return this.proveedor;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setProveedor(String proveedor)
/*  98:    */   {
/*  99:187 */     this.proveedor = proveedor;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BigDecimal getValor()
/* 103:    */   {
/* 104:196 */     return this.valor;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setValor(BigDecimal valor)
/* 108:    */   {
/* 109:206 */     this.valor = valor;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public DocumentoBase getDocumentoBase()
/* 113:    */   {
/* 114:215 */     return this.documentoBase;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 118:    */   {
/* 119:225 */     this.documentoBase = documentoBase;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getDocumento()
/* 123:    */   {
/* 124:234 */     return this.documento;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDocumento(String documento)
/* 128:    */   {
/* 129:244 */     this.documento = documento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getId()
/* 133:    */   {
/* 134:249 */     return this.idPagoAnticipoCheque;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.PagoAnticipoCheque
 * JD-Core Version:    0.7.0.1
 */