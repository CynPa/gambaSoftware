/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.DiaSemanaEnum;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="ruta_vendedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_usuario", "id_sector", "dia_semana"})})
/*  20:    */ public class RutaVendedor
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="ruta_vendedor", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ruta_vendedor")
/*  27:    */   @Column(name="id_ruta_vendedor")
/*  28:    */   private int idRutaVendedor;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_usuario", nullable=true)
/*  35:    */   private EntidadUsuario usuario;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sector", nullable=true)
/*  38:    */   private Sector sector;
/*  39:    */   @Enumerated(EnumType.STRING)
/*  40:    */   @Column(name="dia_semana", length=50, nullable=true)
/*  41:    */   private DiaSemanaEnum diaSemana;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 52 */     return this.idRutaVendedor;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdRutaVendedor()
/*  49:    */   {
/*  50: 56 */     return this.idRutaVendedor;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdRutaVendedor(int idRutaVendedor)
/*  54:    */   {
/*  55: 60 */     this.idRutaVendedor = idRutaVendedor;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdOrganizacion()
/*  59:    */   {
/*  60: 64 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(int idOrganizacion)
/*  64:    */   {
/*  65: 68 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70: 72 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75: 76 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public EntidadUsuario getUsuario()
/*  79:    */   {
/*  80: 80 */     return this.usuario;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setUsuario(EntidadUsuario usuario)
/*  84:    */   {
/*  85: 84 */     this.usuario = usuario;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Sector getSector()
/*  89:    */   {
/*  90: 88 */     return this.sector;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setSector(Sector sector)
/*  94:    */   {
/*  95: 92 */     this.sector = sector;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public DiaSemanaEnum getDiaSemana()
/*  99:    */   {
/* 100: 96 */     return this.diaSemana;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setDiaSemana(DiaSemanaEnum diaSemana)
/* 104:    */   {
/* 105:100 */     this.diaSemana = diaSemana;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RutaVendedor
 * JD-Core Version:    0.7.0.1
 */