/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="saldo_unidad_manejo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_transportista", "id_sucursal", "id_empresa", "id_subempresa", "id_unidad_manejo"})})
/*  17:    */ public class SaldoUnidadManejo
/*  18:    */   extends EntidadBase
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="saldo_unidad_manejo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="saldo_unidad_manejo")
/*  25:    */   @Column(name="id_saldo_unidad_manejo", unique=true, nullable=false)
/*  26:    */   private int idSaldoUnidadManejo;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  31:    */   private Transportista transportista;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  34:    */   private Sucursal sucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  37:    */   private Empresa empresa;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  40:    */   private Subempresa subempresa;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_unidad_manejo", nullable=true)
/*  43:    */   private UnidadManejo unidadManejo;
/*  44:    */   @Column(name="cantidad", nullable=false)
/*  45:    */   private int cantidad;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 79 */     return this.idSaldoUnidadManejo;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdSaldoUnidadManejo()
/*  53:    */   {
/*  54: 86 */     return this.idSaldoUnidadManejo;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdOrganizacion()
/*  58:    */   {
/*  59: 93 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Transportista getTransportista()
/*  63:    */   {
/*  64:100 */     return this.transportista;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Sucursal getSucursal()
/*  68:    */   {
/*  69:107 */     return this.sucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Empresa getEmpresa()
/*  73:    */   {
/*  74:114 */     return this.empresa;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Subempresa getSubempresa()
/*  78:    */   {
/*  79:121 */     return this.subempresa;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSaldoUnidadManejo(int idSaldoUnidadManejo)
/*  83:    */   {
/*  84:129 */     this.idSaldoUnidadManejo = idSaldoUnidadManejo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:137 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTransportista(Transportista transportista)
/*  93:    */   {
/*  94:145 */     this.transportista = transportista;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setSucursal(Sucursal sucursal)
/*  98:    */   {
/*  99:153 */     this.sucursal = sucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setEmpresa(Empresa empresa)
/* 103:    */   {
/* 104:161 */     this.empresa = empresa;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setSubempresa(Subempresa subempresa)
/* 108:    */   {
/* 109:169 */     this.subempresa = subempresa;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getCantidad()
/* 113:    */   {
/* 114:176 */     return this.cantidad;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setCantidad(int cantidad)
/* 118:    */   {
/* 119:184 */     this.cantidad = cantidad;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public UnidadManejo getUnidadManejo()
/* 123:    */   {
/* 124:191 */     return this.unidadManejo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/* 128:    */   {
/* 129:199 */     this.unidadManejo = unidadManejo;
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SaldoUnidadManejo
 * JD-Core Version:    0.7.0.1
 */