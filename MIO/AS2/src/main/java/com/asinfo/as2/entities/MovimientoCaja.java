/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="movimiento_caja")
/*  25:    */ public class MovimientoCaja
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="movimiento_caja", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_caja")
/*  33:    */   @Column(name="id_movimiento_caja", unique=true, nullable=false)
/*  34:    */   private int idMovimientoCaja;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Temporal(TemporalType.DATE)
/*  40:    */   @Column(name="fecha", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private Date fecha;
/*  43:    */   @Column(name="descripcion", nullable=true)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="operacion", nullable=false)
/*  47:    */   private short operacion;
/*  48:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  49:    */   @Digits(integer=12, fraction=2)
/*  50: 68 */   private BigDecimal valor = BigDecimal.ZERO;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_usuario", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private EntidadUsuario usuario;
/*  55:    */   
/*  56:    */   public int getIdMovimientoCaja()
/*  57:    */   {
/*  58:103 */     return this.idMovimientoCaja;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdMovimientoCaja(int idMovimientoCaja)
/*  62:    */   {
/*  63:113 */     this.idMovimientoCaja = idMovimientoCaja;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:122 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:132 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:141 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:151 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Date getFecha()
/*  87:    */   {
/*  88:160 */     return this.fecha;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setFecha(Date fecha)
/*  92:    */   {
/*  93:170 */     this.fecha = fecha;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getDescripcion()
/*  97:    */   {
/*  98:179 */     return this.descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDescripcion(String descripcion)
/* 102:    */   {
/* 103:189 */     this.descripcion = descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public short getOperacion()
/* 107:    */   {
/* 108:198 */     return this.operacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setOperacion(short operacion)
/* 112:    */   {
/* 113:208 */     this.operacion = operacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public BigDecimal getValor()
/* 117:    */   {
/* 118:217 */     return this.valor;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setValor(BigDecimal valor)
/* 122:    */   {
/* 123:227 */     this.valor = valor;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public EntidadUsuario getUsuario()
/* 127:    */   {
/* 128:236 */     return this.usuario;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setUsuario(EntidadUsuario usuario)
/* 132:    */   {
/* 133:246 */     this.usuario = usuario;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getId()
/* 137:    */   {
/* 138:256 */     return this.idMovimientoCaja;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MovimientoCaja
 * JD-Core Version:    0.7.0.1
 */